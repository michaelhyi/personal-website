package com.michaelhyi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michaelhyi.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("{email}")
    public ResponseEntity<String> login(
            @PathVariable("email") String email) {
        return ResponseEntity.ok(service.login(email));
    }

    @GetMapping("validate-token/{token}")
    public ResponseEntity<Void> validateToken(
            @PathVariable("token") String token) {
        service.validateToken(token);
        return ResponseEntity.ok().build();
    }
}