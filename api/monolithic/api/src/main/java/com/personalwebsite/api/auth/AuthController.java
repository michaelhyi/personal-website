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

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AuthDto req
    ) {
        try {
            String token = service.login(req);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            if (e.toString().contains("AuthenticationException"))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            else if (e.toString().contains("NoSuchElementException"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
