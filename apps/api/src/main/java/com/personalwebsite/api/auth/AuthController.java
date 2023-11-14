package com.personalwebsite.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(service.register(req));
    }
}