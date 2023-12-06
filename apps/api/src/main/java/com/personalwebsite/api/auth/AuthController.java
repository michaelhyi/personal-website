package com.personalwebsite.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("{email}")
    public ResponseEntity<String> authenticate(
            @PathVariable("email") String email) {
        return ResponseEntity.ok(service.authenticate(email));
    }
}