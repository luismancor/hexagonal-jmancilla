package com.codigo.msregistro.infraestructure.adapters;

import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;
import com.codigo.msregistro.domain.aggregates.response.ResponseReniec;
import com.codigo.msregistro.domain.ports.out.PersonaServiceOut;
import com.codigo.msregistro.infraestructure.dao.PersonaRepository;
import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import com.codigo.msregistro.infraestructure.mapper.PersonaMapper;
import com.codigo.msregistro.infraestructure.rest.ClienteReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final PersonaRepository personaRepository;
    private final ClienteReniec reniec;
    private final PersonaMapper personaMapper;
    private final RestTemplate restTemplate;

    @Value("${token}")
    private String token;

    @Override
    public ResponseBase crearPersonaOut(RequestPersona persona) {
        PersonaEntity personaEntity = getPersonaEntity(persona);
        if (personaRepository.existsByNumDoc(personaEntity.getNumDoc())) {
            throw new RuntimeException("Ya existe el documento " + personaEntity.getNumDoc());

        } else {
            PersonaDTO pe = personaMapper.mapToDto(personaRepository.save(personaEntity));
            if (pe != null) {
                return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(pe));
            } else {
                return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
            }
        }
    }

    @Override
    public ResponseBase buscarPersonaOut(String numDoc) {
        PersonaDTO personaDTO = personaMapper.mapToDto(personaRepository.findByNumDoc(numDoc));
        if (personaDTO != null) {
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(personaDTO));
        } else {
            return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
        }
    }


    @Override
    public ResponseBase obtenerTodosOut() {
        List<PersonaDTO> personaDTOS = new ArrayList<>();
        List<PersonaEntity> entityList = personaRepository.findByEstado(Constants.ESTADO_ACTIVO);
        for (PersonaEntity entity : entityList) {
            PersonaDTO personaDTO = personaMapper.mapToDto(entity);
            personaDTOS.add(personaDTO);
        }
        if (personaDTOS.isEmpty()) {
            return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
        } else {
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(personaDTOS));
        }
    }

    @Override
    public ResponseBase actualizarPersonaOut(Long id, RequestPersona requestPersona) {
        boolean existe = personaRepository.existsById(id);
        if (existe) {
            Optional<PersonaEntity> persona = personaRepository.findById(id);
            PersonaDTO pe = personaMapper.mapToDto(personaRepository.save(getEntityUpdate(requestPersona, persona.get())));
            if (pe != null) {
                return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(pe));
            } else {
                return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
            }
        }
        return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
    }

    @Override
    public ResponseBase borrarOut(Long id) {
        boolean existe = personaRepository.existsById(id);
        if (existe) {
            Optional<PersonaEntity> persona = personaRepository.findById(id);
            persona.get().setEstado(Constants.ESTADO_INACTIVO);
            persona.get().setUsuaDelete(Constants.USU_AUDIT);
            persona.get().setDateDelete(getTime());
            personaRepository.save(persona.get());
            PersonaDTO result = personaMapper.mapToDto(persona.get());
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(result));
        } else {
            return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
        }
    }

    private PersonaEntity getPersonaEntity(RequestPersona requestPersona) {
        ResponseReniec responseReniec = getEntityRestTemplate(requestPersona);
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setNumDoc(responseReniec.getNumeroDocumento());
        personaEntity.setNombre(responseReniec.getNombres());
        personaEntity.setApellido(responseReniec.getApellidoPaterno() + responseReniec.getApellidoMaterno());
        personaEntity.setTipoDoc(responseReniec.getTipoDocumento());
        personaEntity.setEstado(Constants.ESTADO_ACTIVO);
        personaEntity.setUsuaCrea(Constants.USU_ADMIN);
        personaEntity.setDateCrea(getTime());
        return personaEntity;
    }

    private PersonaEntity getEntityUpdate(RequestPersona requestPersona, PersonaEntity personaEntity) {
//        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setEdad(requestPersona.getEdad());
        personaEntity.setCargo(requestPersona.getCargo());
        personaEntity.setSalario(requestPersona.getSalario());
        personaEntity.setTelefono(requestPersona.getTelefono());
        personaEntity.setCorreo(requestPersona.getCorreo());
        personaEntity.setDepartamento(requestPersona.getDepartamento());
        personaEntity.setEstado(Constants.ESTADO_ACTIVO);
        personaEntity.setUsuaUpdate(Constants.USU_AUDIT);
        personaEntity.setDateUpdate(getTime());
        return personaEntity;
    }

    private Timestamp getTime() {
        long current = System.currentTimeMillis();
        return new Timestamp(current);
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private ResponseReniec getEntityRestTemplate(RequestPersona requestPersona) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + requestPersona.getNumDoc();
        try {
            ResponseEntity<ResponseReniec> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders(token)),
                    ResponseReniec.class
            );
            ResponseReniec responseReniec = response.getBody();
            return responseReniec;
        } catch (HttpClientErrorException e) {
            System.err.println("ERROR AL CONSUMIR EL API EXTERNA " + e.getStatusCode());
        }
        return null;
    }


}
