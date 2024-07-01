package com.codigo.msregistro.domain.impl;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;
import com.codigo.msregistro.domain.ports.in.PersonaServiceIn;
import com.codigo.msregistro.domain.ports.out.PersonaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;
    @Override
    public ResponseBase crearPersonaIn(RequestPersona persona) throws IOException {
        return personaServiceOut.crearPersonaOut(persona);
    }

    @Override
    public ResponseBase buscarPersonaIn(String numDoc) {
        return personaServiceOut. buscarPersonaOut(numDoc);
    }

    @Override
    public ResponseBase obtenerTodosIn() {
        return personaServiceOut.obtenerTodosOut();
    }

    @Override
    public ResponseBase actualizarPersonaIn(Long id, RequestPersona personaRequest) {
        return personaServiceOut.actualizarPersonaOut(id, personaRequest);
    }


    @Override
    public ResponseBase borrarIn(Long id) {
        return personaServiceOut.borrarOut(id);
    }



}
