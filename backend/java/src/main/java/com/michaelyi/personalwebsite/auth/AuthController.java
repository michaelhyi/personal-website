package com.michaelyi.personalwebsite.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest req) {
        return service.login(req);
    }

    @GetMapping("/validate-token")
    public void validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        service.validateToken(authHeader);
    }
}
