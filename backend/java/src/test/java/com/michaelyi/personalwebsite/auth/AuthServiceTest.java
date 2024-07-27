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
    private PasswordEncoder passwordEncoder;

    private static final String AUTHORIZED_PASSWORD = "authorized";
    private static final String UNAUTHORIZED_PASSWORD = "unauthorized password";
    private static final String SIGNING_KEY
            = "fakesigninkeyfakesigninkeyfakesigninkeyfakesigninkey";
    private static final String ADMIN_PASSWORD = "encoded password";

    @BeforeEach
    void setUp() {
        service = new AuthService(
                ADMIN_PASSWORD,
                SIGNING_KEY,
                passwordEncoder
        );
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        AuthRequest req = new AuthRequest(
                UNAUTHORIZED_PASSWORD
        );

        Mockito.when(passwordEncoder.matches(
                req.password(),
                ADMIN_PASSWORD
        )).thenReturn(false);

        assertThrows(
                UnauthorizedException.class, () ->
                        service.login(req)
        );

        Mockito.verify(passwordEncoder).matches(
                req.password(),
                ADMIN_PASSWORD
        );
    }

    @Test
    void login() {
        AuthRequest req = new AuthRequest(
                AUTHORIZED_PASSWORD
        );

        Mockito.when(passwordEncoder.matches(
                req.password(),
                ADMIN_PASSWORD
        )).thenReturn(true);

        service.login(req);
        Mockito.verify(passwordEncoder).matches(
                req.password(),
                ADMIN_PASSWORD
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
                () -> service.validateToken(String.format("Bearer %s", token))
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

        service.validateToken(String.format("Bearer %s", token));
    }
}
