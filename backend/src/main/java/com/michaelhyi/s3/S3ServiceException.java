package com.michaelhyi.s3;

public class S3ServiceException extends RuntimeException {
    public S3ServiceException() {
        super("An error occurred with the AWS S3 service.");

    }
}
