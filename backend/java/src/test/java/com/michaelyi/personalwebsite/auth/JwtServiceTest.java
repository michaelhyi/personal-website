package com.michaelyi.personalwebsite.auth;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class JwtServiceTest {
    private JwtService underTest;
    private static String jwtSecret;
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    @BeforeAll
    static void beforeAll() {
        jwtSecret = AuthTestHelper.generateJwtSecret();
    }

    @BeforeEach
    void setup() {
        underTest = new JwtService(jwtSecret, JWT_EXPIRATION);
    }

    @Test
    void canGetSigningKey() {
        // when
        SecretKey actual = underTest.getSigningKey();

        // then
        assertNotNull(actual);
    }

    @Test
    void canGenerateToken() {
        // when
        String token = underTest.generateToken();

        // then
        SecretKey signingKey = underTest.getSigningKey();
        Jwts
                .parser()
                .verifyWith(signingKey)
                .requireSubject(AuthUtil.ADMIN_EMAIL)
                .build()
                .parseSignedClaims(token);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsNotJwt() {
        // given
        String token = "not jwt";

        // when
        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        // then
        assertEquals("Unauthorized", err.getMessage());
    }

    @Test
    void willThrowValidateTokenWhenTokenUsesWrongSigningKey() {
        // given
        String wrongSecret = AuthTestHelper.generateJwtSecret();
        JwtService fakeService = new JwtService(
                wrongSecret,
                JWT_EXPIRATION);
        String token = fakeService.generateToken();

        // when
        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        // then
        assertEquals("Unauthorized", err.getMessage());
    }

    @Test
    void willThrowValidateTokenWhenTokenIsExpired() {
        // given
        JwtService fakeService = new JwtService(
                jwtSecret,
                JWT_EXPIRATION * -1);
        String token = fakeService.generateToken();

        // when
        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        // then
        assertEquals("Unauthorized", err.getMessage());
    }

    @Test
    void canValidateToken() {
        // given
        String token = underTest.generateToken();

        // when
        underTest.validateToken(token);
    }
}
