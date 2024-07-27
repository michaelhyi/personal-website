package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.michaelyi.personalwebsite.util.Constants.JWT_EXPIRATION;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService service;

    @Mock
    private PasswordEncoder encoder;

    private static final String AUTHORIZED_PASSWORD = "authorized password";
    private static final String UNAUTHORIZED_PASSWORD = "unauthorized password";
    private static final String SIGNING_KEY = "signing key";
    private String adminPassword;

    @BeforeEach
    void setUp() {
        adminPassword = encoder.encode(AUTHORIZED_PASSWORD);

        service = new AuthService(
                adminPassword,
                SIGNING_KEY,
                encoder
        );
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        AuthRequest unauthorizedLoginRequest = new AuthRequest(
                UNAUTHORIZED_PASSWORD
        );

        assertThrows(
                UnauthorizedException.class, () ->
                        service.login(unauthorizedLoginRequest)
        );

        Mockito.verify(encoder).matches(
                unauthorizedLoginRequest.password(),
                adminPassword
        );
    }

    @Test
    void login() {
        AuthRequest authorizedLoginRequest = new AuthRequest(
                AUTHORIZED_PASSWORD
        );

        service.login(authorizedLoginRequest);
        Mockito.verify(encoder).matches(
                authorizedLoginRequest.password(),
                adminPassword
        );
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        Map<String, Object> claims = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        String token =
                Jwts
                        .builder()
                        .setClaims(claims)
                        .setSubject(Constants.ADMIN_EMAIL)
                        .setIssuedAt(new Date(currentTime))
                        .setExpiration(new Date(currentTime - JWT_EXPIRATION))
                        .signWith(
                                AuthUtil.getSigningKey(SIGNING_KEY),
                                SignatureAlgorithm.HS256
                        )
                        .compact();

        assertThrows(
                UnauthorizedException.class,
                () -> service.validateToken(token)
        );
    }

    @Test
    void validateToken() {
        Map<String, Object> claims = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        String token =
                Jwts
                        .builder()
                        .setClaims(claims)
                        .setSubject(Constants.ADMIN_EMAIL)
                        .setIssuedAt(new Date(currentTime))
                        .setExpiration(new Date(currentTime + JWT_EXPIRATION))
                        .signWith(
                                AuthUtil.getSigningKey(SIGNING_KEY),
                                SignatureAlgorithm.HS256
                        )
                        .compact();

        service.validateToken(token);
    }
}
