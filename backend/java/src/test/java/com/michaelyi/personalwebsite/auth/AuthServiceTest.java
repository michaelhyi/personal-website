package com.michaelyi.personalwebsite.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                AuthTestUtil.FAKE_SIGNING_KEY,
                passwordEncoder);
    }

    @Test
    void willThrowLoginWhenBadRequest() {
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(null));

        assertEquals("Password cannot be empty", err.getMessage());

        err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(""));

        assertEquals("Password cannot be empty", err.getMessage());

        err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(" "));

        assertEquals("Password cannot be empty", err.getMessage());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        when(passwordEncoder.matches(
                "unauthorized",
                ADMIN_PASSWORD)).thenReturn(false);

        assertThrows(
                UnauthorizedException.class,
                () -> underTest.login("unauthorized"));

        verify(passwordEncoder).matches(
                "unauthorized",
                ADMIN_PASSWORD);
    }

    @Test
    void login() {
        when(passwordEncoder.matches("authorized", ADMIN_PASSWORD))
                .thenReturn(true);
        underTest.login("authorized");
        verify(passwordEncoder).matches(
                "authorized",
                ADMIN_PASSWORD);
    }

    @Test
    void willThrowValidateTokenWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(null));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(""));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(" "));
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        String token = AuthTestUtil.generateBadToken(
                AuthUtil.JWT_EXPIRATION * -1);
        assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));
    }

    @Test
    void validateToken() {
        String token = AuthTestUtil.generateBadToken(
                AuthUtil.JWT_EXPIRATION);
        underTest.validateToken(token);
    }
}
