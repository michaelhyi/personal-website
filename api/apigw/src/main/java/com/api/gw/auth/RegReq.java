package com.api.gw.auth;

import com.api.gw.user.Role;

import java.util.List;

public record RegReq(
        String email,
        String password,
        boolean enabled,
        List<Role> roles
) {
}
