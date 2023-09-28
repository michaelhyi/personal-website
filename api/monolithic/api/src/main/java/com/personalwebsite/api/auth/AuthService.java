package com.personalwebsite.api.auth;

import com.personalwebsite.api.security.JwtService;
import com.personalwebsite.api.token.Token;
import com.personalwebsite.api.token.TokenRepository;
import com.personalwebsite.api.token.TokenType;
import com.personalwebsite.api.user.User;
import com.personalwebsite.api.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,
                       TokenRepository tokenRepository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String login(AuthDto req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        User user = repository.findByEmail(req.email())
                .orElseThrow(() -> new NoSuchElementException("Email does not exist."));

        String token = jwtService.generateToken(user);
        revokeAllUserTokens(user);

        saveUserToken(user, token);

        return token;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(jwtToken, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}