package com.michaelhyi.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.michaelhyi.security.JwtService;
import com.michaelhyi.user.User;
import com.michaelhyi.user.UserRepository;

import lombok.AllArgsConstructor;

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
        String token = bearerToken.substring(7);
        String email = jwtService.extractUsername(token);
        User user = repository
                .findById(email)
                .orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(token, user)) {
            throw new UnauthorizedUserException();
        }
    }
}
