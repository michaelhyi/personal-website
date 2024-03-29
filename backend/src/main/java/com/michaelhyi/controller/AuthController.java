package com.michaelhyi.controller;

import com.michaelhyi.dto.LoginRequest;
import com.michaelhyi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("login")
    @Cacheable(value = "login", key = "#req.email()")
    public String login(@RequestBody LoginRequest req) {
        return service.login(req.email());
    }

    @GetMapping("validate-token")
    public void validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        service.validateToken(token);
    }
}