package com.michaelhyi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.michaelhyi.Jwt.JwtService;
import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.entity.User;
import com.michaelhyi.exception.UnauthorizedUserException;
import com.michaelhyi.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final List<String> whitelistedEmails;

    public String login(String email) {
        Optional<User> user = repository.findById(email);

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

    public void validateToken(String token) {
        String email = jwtService.extractUsername(token);
        User user = repository
                .findById(email)
                .orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(token, user)) {
            throw new UnauthorizedUserException();
        }
    }
}