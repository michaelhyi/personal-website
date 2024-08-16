package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.util.HttpResponse;

public class LoginResponse extends HttpResponse {
    private String token;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
