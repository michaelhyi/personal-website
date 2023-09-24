package com.api.gw.auth;

public record AuthReq(
        String email,
        String password
) {
}
