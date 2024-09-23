package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.util.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException e
    ) {
        HttpResponse res = new HttpResponse();
        res.setError(e.getMessage());
        res.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
