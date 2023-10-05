package com.personalwebsite.api.user;

import com.personalwebsite.api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserRepository repository;
    private final JwtService jwtService;

    public UserController(UserRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @GetMapping("{token}")
    public ResponseEntity<User> readByToken(@PathVariable("token") String token) {
        if (token.equals("null") || token.equals("undefined"))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        String email = jwtService.extractUsername(token);
        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        if (!jwtService.isTokenValid(token, user.get()))
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();

        return ResponseEntity.ok(user.get());
    }
}