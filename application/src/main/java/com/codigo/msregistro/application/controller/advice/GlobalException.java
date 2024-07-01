package com.codigo.msregistro.application.controller.advice;

import com.codigo.msregistro.application.controller.personalizada.PersonaException;
import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.response.ResponseBase;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBase> manejandoExcepciones(Exception ex){
        log.error("Error manejado desde: manejandoExcepciones ");
        ResponseBase responseBase = new ResponseBase(Constants.CODIGO_ERROR, "Error interno del servidor", Optional.empty());
        return new ResponseEntity<>(responseBase, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseBase> manejandoNullPointer(NullPointerException ex){
        log.error("Error manejado desde: manejandoNullPointer ");
        ResponseBase responseBase = new ResponseBase(Constants.CODIGO_ERROR, "Error existe un dato nulo "+ ex.getMessage(),Optional.empty());
        return new ResponseEntity<>(responseBase, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseBase> manejandoIoException(IOException ex){
        log.error("Error manejado desde: manejandoIoException ");
        ResponseBase responseBase = new ResponseBase(Constants.CODIGO_ERROR, "Error de io exception "+ ex.getMessage(),Optional.empty());
        return new ResponseEntity<>(responseBase, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseBase> manejandoRunTimeException(RuntimeException ex){
        log.error("Error manejado desde: manejandoRunTimeException ");
        ResponseBase responseBase = new ResponseBase(Constants.CODIGO_ERROR, "Error en tiempo de ejecucion runtime "+ ex.getMessage(),Optional.empty());
        return new ResponseEntity<>(responseBase, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PersonaException.class)
    public ResponseEntity<ResponseBase> manejandoPersonaException(PersonaException ex){
        log.error("Error manejado desde: manejandoPersonaException ");
        ResponseBase responseBase = new ResponseBase(Constants.CODIGO_ERROR, "Error en la persona "+ ex,Optional.empty());
        return new ResponseEntity<>(responseBase, HttpStatus.CONFLICT);
    }
}
