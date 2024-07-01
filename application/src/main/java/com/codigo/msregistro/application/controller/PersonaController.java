package com.codigo.msregistro.application.controller;

import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;
import com.codigo.msregistro.domain.ports.in.PersonaServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaServiceIn personaServiceIn;

    @PostMapping
    public ResponseEntity<ResponseBase> registrar(@RequestBody RequestPersona persona) throws IOException {
        ResponseBase responseBase = personaServiceIn.crearPersonaIn(persona);
        if (responseBase.getCode() == Constants.CODIGO_EXITO) {
            return ResponseEntity.ok(responseBase);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @GetMapping("/{numDoc}")
    public ResponseEntity<ResponseBase> buscarPersona(@PathVariable String numDoc) {
        ResponseBase responseBase = personaServiceIn.buscarPersonaIn(numDoc);
        if (responseBase.getCode() == Constants.CODIGO_EXITO) {
            return ResponseEntity.ok(responseBase);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @GetMapping()
    public ResponseEntity<ResponseBase> listarTodos() {
        ResponseBase responseBase = personaServiceIn.obtenerTodosIn();
        if (responseBase.getCode() == Constants.CODIGO_EXITO) {
            return ResponseEntity.ok(responseBase);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBase> actualizarPersona(@PathVariable Long id, @RequestBody RequestPersona requestPersona) {
        ResponseBase responseBase = personaServiceIn.actualizarPersonaIn(id, requestPersona);
        if (responseBase.getCode() == Constants.CODIGO_EXITO) {
            return ResponseEntity.ok(responseBase);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase> borrar(@PathVariable Long id) {
        personaServiceIn.borrarIn(id);
        return ResponseEntity.ok(new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, Optional.empty()));
    }

}
