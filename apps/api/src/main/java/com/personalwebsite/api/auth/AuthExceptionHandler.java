package com.personalwebsite.api.auth;

import com.personalwebsite.api.auth.exceptions.InvalidCredentialsException;
import com.personalwebsite.api.auth.exceptions.UnauthorizedUserException;
import com.personalwebsite.api.auth.exceptions.UserAlreadyExistsException;
import com.personalwebsite.api.auth.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {InvalidCredentialsException.class,
            UnauthorizedUserException.class,
            UserAlreadyExistsException.class,
            UserNotFoundException.class}
    )
    protected ResponseEntity<Object> handleException(
            RuntimeException e, WebRequest req) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof UnauthorizedUserException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof UserAlreadyExistsException) {
            status = HttpStatus.FORBIDDEN;
        } else if (e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), status, req);
    }
}