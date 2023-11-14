package com.personalwebsite.api.auth;

import com.personalwebsite.api.security.JwtService;
import com.personalwebsite.api.user.User;
import com.personalwebsite.api.user.UserRepository;
import com.personalwebsite.api.user.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String login(AuthDto req) {
        User user = repository.findByEmail(req.email())
                .orElseThrow(() ->
                        new IllegalStateException("User does not exist.")
                );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        return jwtService.generateToken(user);
    }

    public String register(AuthDto req) {
        if (repository.findByEmail(req.email()).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }

        boolean authorized = false;

        for (AuthWhitelistedUsers user : AuthWhitelistedUsers.values()) {
            if (user.getEmail().equals(req.email())) {
                authorized = true;
                break;
            }
        }

        if (!authorized) {
            throw new IllegalArgumentException("Unauthorized.");
        }

        User user = new User(req.email(),
                passwordEncoder.encode(req.password()),
                UserRole.ADMIN);
        repository.save(user);
        return jwtService.generateToken(user);
    }
}