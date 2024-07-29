package com.michaelyi.personalwebsite.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            JwtException.class,
            UnauthorizedException.class,
            SignatureException.class
    })
    public ResponseEntity<String> handleUnauthorizedException() {
        return new ResponseEntity<>(
                "Unauthorized",
                HttpStatus.UNAUTHORIZED
        );
    }
}