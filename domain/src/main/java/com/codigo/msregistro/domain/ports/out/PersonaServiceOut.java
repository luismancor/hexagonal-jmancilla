package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    ResponseBase crearPersonaOut(RequestPersona persona);
    ResponseBase buscarPersonaOut(String numDoc);
    ResponseBase obtenerTodosOut();

    ResponseBase actualizarPersonaOut(Long id, RequestPersona requestPersona);
    ResponseBase borrarOut(Long id);

}
