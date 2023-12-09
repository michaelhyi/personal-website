package com.personalwebsite.api.exception;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Unauthorized.");
    }
}
