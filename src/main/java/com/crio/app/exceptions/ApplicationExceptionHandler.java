package com.crio.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crio.app.exchange.Response;

@RestControllerAdvice
public class ApplicationExceptionHandler { 
    
    @ExceptionHandler(UserAlreadyRegister.class)
    public ResponseEntity<Response> userAlreadyRegister(UserAlreadyRegister e) {
        Response error = new Response(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Response> uerIdNotFound(UserIdNotFoundException e) {
        Response error = new Response(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
