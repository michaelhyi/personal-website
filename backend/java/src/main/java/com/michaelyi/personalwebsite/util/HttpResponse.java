package com.michaelyi.personalwebsite.util;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    private String error;
    private HttpStatus httpStatus;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus status) {
        this.httpStatus = status;
    }
}