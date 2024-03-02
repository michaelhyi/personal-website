package com.michaelhyi.exception;

public class S3ObjectNotFoundException extends RuntimeException {
    public S3ObjectNotFoundException() {
        super("S3 object not found.");
    }
}
