package com.michaelyi.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("${api.version-path}/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return service.login(req);
    }

    @GetMapping("/validate-token")
    public void validateToken(
            @RequestHeader("Authorization") String bearerToken
    ) {
        service.validateToken(bearerToken);
    }
}
