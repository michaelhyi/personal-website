package com.michaelyi.personalwebsite.util;

public class Response<T> {
    private T value;
    private String error;

    public Response(T value, String error) {
        this.value = value;
        this.error = error;
    }

    public T getValue() {
        return value;
    }

    public String getError() {
        return error;
    }
}
