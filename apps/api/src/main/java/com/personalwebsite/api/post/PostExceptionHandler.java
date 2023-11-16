package com.personalwebsite.api.post;

import com.personalwebsite.api.post.exceptions.PostNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PostExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {PostNotFoundException.class, IllegalArgumentException.class}
    )
    protected ResponseEntity<Object> handleException(
            RuntimeException e, WebRequest req) {

        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }
}