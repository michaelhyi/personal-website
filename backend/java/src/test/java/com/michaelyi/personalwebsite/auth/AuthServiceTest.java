package com.michaelyi.personalwebsite.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.security.Key;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService underTest;

    @Mock
    private PasswordEncoder passwordEncoder;
    private static final String ADMIN_PASSWORD = "admin";

    @BeforeEach
    void setUp() {
        underTest = new AuthService(
                ADMIN_PASSWORD,
                AuthTestHelper.FAKE_SIGNING_KEY,
                passwordEncoder);
    }

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsNull() {
        String password = null;
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(password));
        assertEquals("Password cannot be empty", err.getMessage());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsEmpty() {
        String password = "";
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(password));
        assertEquals("Password cannot be empty", err.getMessage());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsBlank() {
        String password = " ";
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(password));
        assertEquals("Password cannot be empty", err.getMessage());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowUnauthorizedDuringLoginWhenWrongPassword() {
        String password = "wrong";

        when(passwordEncoder.matches(
                password,
                ADMIN_PASSWORD)).thenReturn(false);

        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.login(password));
        assertEquals("Wrong password", err.getMessage());
        verify(passwordEncoder).matches(password, ADMIN_PASSWORD);
    }

    @Test
    void willReturnTokenWhenLoginSuccess() {
        String password = "correct";

        when(passwordEncoder.matches(password, ADMIN_PASSWORD))
                .thenReturn(true);

        String token = underTest.login(password);
        Key signingKey = AuthUtil.getSigningKey(
                AuthTestHelper.FAKE_SIGNING_KEY);
        boolean isJwt = Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .isSigned(token);

        assertTrue(isJwt);
        verify(passwordEncoder).matches(password, ADMIN_PASSWORD);
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenIsNull() {
        String token = null;
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(token));
        assertEquals("Token cannot be empty", err.getMessage());
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenIsEmpty() {
        String token = "";
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(token));
        assertEquals("Token cannot be empty", err.getMessage());
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenIsBlank() {
        String token = " ";
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(token));
        assertEquals("Token cannot be empty", err.getMessage());
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenIsNotJwt() {
        String token = "not jwt";
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(token));
        assertEquals("JWT strings must contain exactly 2 period characters. Found: 0", err.getMessage());
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenUsesWrongSigningKey() {
        String token = AuthTestHelper.generateToken(
                "wrongsigningkeywrongsigningkeywrongsigningkeywrongsigningkey",
                AuthUtil.JWT_EXPIRATION);

        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        assertEquals("Unauthorized", err.getMessage());
    }

    @Test
    void willThrowBadRequestDuringValidateTokenWhenTokenIsExpired() {
        String token = AuthTestHelper.generateToken(
                AuthTestHelper.FAKE_SIGNING_KEY,
                AuthUtil.JWT_EXPIRATION * -1);

        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        assertEquals("Unauthorized", err.getMessage());
    }

    @Test
    void willValidateTokenWhenTokenIsAuthorized() {
        String token = AuthTestHelper.generateToken(
                AuthTestHelper.FAKE_SIGNING_KEY,
                AuthUtil.JWT_EXPIRATION);
        underTest.validateToken(token);
    }
}
