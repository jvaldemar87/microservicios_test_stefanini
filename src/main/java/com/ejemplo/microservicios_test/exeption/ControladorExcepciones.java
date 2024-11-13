package com.ejemplo.microservicios_test.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControladorExcepciones {

    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<String> handleUsuarioYaExisteException(UsuarioYaExisteException ex) {
        // Retornamos el mensaje de la excepción y un código HTTP 409
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}