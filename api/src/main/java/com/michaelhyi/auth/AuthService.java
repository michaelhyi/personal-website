package com.michaelhyi.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.michaelhyi.exception.UnauthorizedUserException;
import com.michaelhyi.exception.UserNotFoundException;
import com.michaelhyi.security.JwtService;
import com.michaelhyi.user.User;
import com.michaelhyi.user.UserRepository;

@Service
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final List<String> whitelistedEmails;

    public AuthService(UserRepository repository,
                       JwtService jwtService,
                       List<String> whitelistedEmails) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.whitelistedEmails = whitelistedEmails;
    }

    public String authenticate(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            return jwtService.generateToken(user.get());
        }

        boolean authorized = false;

        for (String whitelistedEmail : whitelistedEmails) {
            if (whitelistedEmail.equals(email)) {
                authorized = true;
                break;
            }
        }

        if (!authorized) {
            throw new UnauthorizedUserException();
        }

        User newUser = new User(email);
        repository.save(newUser);

        return jwtService.generateToken(newUser);
    }

    public boolean validateToken(String token) {
        String email = jwtService.extractUsername(token);
        User user = repository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return jwtService.isTokenValid(token, user);
    }
}