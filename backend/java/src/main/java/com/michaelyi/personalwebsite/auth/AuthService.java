package com.michaelyi.personalwebsite.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.michaelyi.personalwebsite.util.StringUtil;

@Service
public class AuthService {
    private final String encodedAdminPassword;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            @Value("${admin.pw}") String encodedAdminPassword,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.encodedAdminPassword = encodedAdminPassword;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String password) {
        if (StringUtil.isStringInvalid(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        boolean authorized = passwordEncoder
                .matches(password, encodedAdminPassword);

        if (!authorized) {
            throw new UnauthorizedException("Wrong password");
        }

        return jwtService.generateToken();
    }

    public void validateToken(String token) {
        if (StringUtil.isStringInvalid(token)) {
            throw new IllegalArgumentException("Token cannot be empty");
        }

        try {
            jwtService.validateToken(token);
        } catch (IllegalArgumentException | UnauthorizedException e) {
            throw e;
        }
    }
}
