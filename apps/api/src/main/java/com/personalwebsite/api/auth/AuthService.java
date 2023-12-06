package com.personalwebsite.api.auth;

import com.personalwebsite.api.exception.UnauthorizedUserException;
import com.personalwebsite.api.security.JwtService;
import com.personalwebsite.api.user.User;
import com.personalwebsite.api.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthWhitelistedEmails authWhitelistedEmails;

    public AuthService(UserRepository repository,
                       JwtService jwtService,
                       AuthWhitelistedEmails authWhitelistedEmails) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authWhitelistedEmails = authWhitelistedEmails;
    }

    public String authenticate(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            return jwtService.generateToken(user.get());
        }

        boolean authorized = false;

        List<String> whitelistedEmails = authWhitelistedEmails
                .getWhitelistedEmails();

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
}