package com.personalwebsite.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(
            @RequestBody AuthDto req
    ) {
        try {
            return ResponseEntity.ok(service.login(req));
        } catch (Exception e) {
            if (e.toString().contains("User does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(
            @RequestBody AuthDto req
    ) {
        try {
            return ResponseEntity.ok(service.register(req));
        } catch (Exception e) {
            if (e.toString().contains("User already exists.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}