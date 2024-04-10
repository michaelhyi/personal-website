package com.michaelhyi.service;

import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.entity.User;
import com.michaelhyi.exception.UnauthorizedUserException;
import com.michaelhyi.exception.UserNotFoundException;
import com.michaelhyi.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    @Value("${auth.whitelisted-emails}")
    private final List<String> whitelistedEmails;
    private final UserRepository repository;
    private final JwtService jwtService;

    public String login(String email) {
        Optional<User> user = repository.findById(email);

        if (user.isPresent()) {
            return jwtService.generateToken(user.get());
        }

        boolean authorized = whitelistedEmails
                .stream()
                .anyMatch(e -> e.equals(email));

        if (!authorized) {
            throw new UnauthorizedUserException();
        }

        User newUser = new User(email);
        repository.save(newUser);
        return jwtService.generateToken(newUser);
    }

    public void validateToken(String bearerToken) {
        String email = jwtService.extractUsername(bearerToken.substring(7));
        User user = repository
                .findById(email)
                .orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(bearerToken, user)) {
            throw new UnauthorizedUserException();
        }
    }
}
