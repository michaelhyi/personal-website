package com.michaelhyi.exception;

public class S3Exception extends RuntimeException {
    public S3Exception() {
        super("An error occurred while interacting with AWS S3.");
    }
}
