package com.michaelhyi.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.michaelhyi.dto.LoginRequest;
import com.michaelhyi.service.AuthService;

import lombok.AllArgsConstructor;

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

    @GetMapping("validate-token/{token}")
    public void validateToken(@PathVariable("token") String token) {
        service.validateToken(token);
    }
}