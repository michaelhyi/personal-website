package com.michaelhyi.auth;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Unauthorized.");
    }
}
