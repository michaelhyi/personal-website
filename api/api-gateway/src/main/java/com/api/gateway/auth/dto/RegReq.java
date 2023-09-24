package com.api.gateway.auth.dto;

import com.api.gateway.user.UserRole;

import java.util.List;

public record RegReq(
        String email,
        String password,
        boolean enabled,
        List<UserRole> roles
) {
}
