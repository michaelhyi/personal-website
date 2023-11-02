package com.personalwebsite.api.auth;

public record AuthDto(
        String email,
        String password
) {
}
