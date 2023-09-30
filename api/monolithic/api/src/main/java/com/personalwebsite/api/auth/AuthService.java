package com.personalwebsite.api.auth;

import com.personalwebsite.api.security.JwtService;
import com.personalwebsite.api.user.User;
import com.personalwebsite.api.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String login(AuthDto req) {
        User user = repository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalStateException("Email does not exist."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        String token = jwtService.generateToken(user);
        return token;
    }

}