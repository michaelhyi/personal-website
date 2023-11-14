package com.personalwebsite.api.user;

import com.personalwebsite.api.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final JwtService jwtService;

    public UserService(UserRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public User readUserByToken(String token) {
        if (token == null
                || token.isBlank()
                || token.isEmpty()
                || token.equals("null")
                || token.equals("undefined")) {
            throw new RuntimeException();
        }


        String email = jwtService.extractUsername(token);
        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException();
        }


        if (!jwtService.isTokenValid(token, user.get())) {
            throw new RuntimeException();
        }

        return user.get();
    }
}
