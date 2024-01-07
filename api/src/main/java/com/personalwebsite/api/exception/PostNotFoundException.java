package com.personalwebsite.api.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post not found.");
    }
}
