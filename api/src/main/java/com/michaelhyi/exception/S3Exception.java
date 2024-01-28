package com.michaelhyi.exception;

public class S3Exception extends RuntimeException {
    public S3Exception() {
        super("S3 is unable complete that action.");
    }
}
