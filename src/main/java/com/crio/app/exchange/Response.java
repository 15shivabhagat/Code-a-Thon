package com.crio.app.exchange;

import org.springframework.http.HttpStatus;

public record Response(HttpStatus status, String message) {

    
}
