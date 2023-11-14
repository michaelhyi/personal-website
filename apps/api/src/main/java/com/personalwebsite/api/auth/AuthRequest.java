package com.personalwebsite.api.auth;

public record AuthRequest(
        String email,
        String password,
        String confirmPassword
) {
}