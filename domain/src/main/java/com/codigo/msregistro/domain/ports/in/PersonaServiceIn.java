package com.codigo.msregistro.domain.ports.in;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    ResponseBase crearPersonaIn(RequestPersona persona) throws IOException;
    ResponseBase buscarPersonaIn(String numDoc);
    ResponseBase obtenerTodosIn();
    ResponseBase actualizarPersonaIn(Long id, RequestPersona personaRequest);
    ResponseBase borrarIn(Long id);
}
