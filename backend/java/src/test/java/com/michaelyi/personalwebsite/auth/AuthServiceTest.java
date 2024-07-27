package com.michaelyi.personalwebsite.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService service;

    @Mock
    private PasswordEncoder encoder;

    private JwtParserBuilder jwtParserBuilder;
    private JwtParser jwtParser;
    private Claims claims;

    private static final String AUTHORIZED_PASSWORD = "authorized password";
    private static final String UNAUTHORIZED_PASSWORD = "unauthorized password";
    private static final String TOKEN = "token";
    private static final String SIGNING_KEY = "signing key";
    private static final int EXPIRATION = 15000;

    @BeforeEach
    void setUp() {
        jwtParserBuilder = Mockito.mock(JwtParserBuilder.class);
        jwtParser = Mockito.mock(JwtParser.class);
        claims = Mockito.mock(Claims.class);

        String encodedAuthorizedPassword = encoder.encode(AUTHORIZED_PASSWORD);

        service = new AuthService(
                encodedAuthorizedPassword,
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
                AUTHORIZED_PASSWORD
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
                AUTHORIZED_PASSWORD
        );
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        when(Jwts.parserBuilder()).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.setSigningKey(
                AuthUtil.getSigningKey(SIGNING_KEY))
        ).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(TOKEN).getBody()).thenReturn(claims);
        when(claims.getExpiration())
                .thenReturn(new Date(System.currentTimeMillis() - EXPIRATION));

        assertThrows(
                UnauthorizedException.class,
                () -> service.validateToken(TOKEN)
        );
    }

    @Test
    void validateToken() {
        when(Jwts.parserBuilder()).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.setSigningKey(
                AuthUtil.getSigningKey(SIGNING_KEY))
        ).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(TOKEN).getBody()).thenReturn(claims);
        when(claims.getExpiration())
                .thenReturn(new Date(System.currentTimeMillis() + EXPIRATION));

        service.validateToken(TOKEN);
    }
}
