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

import io.jsonwebtoken.ExpiredJwtException;

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
                AuthTestUtil.SIGNING_KEY,
                passwordEncoder);
    }

    @Test
    void willThrowLoginWhenBadRequest() {
        AuthRequest emptyReq = new AuthRequest("");

        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(emptyReq));

        assertEquals("Password cannot be empty", err.getMessage());

        AuthRequest blankReq = new AuthRequest(" ");

        err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(blankReq));

        assertEquals("Password cannot be empty", err.getMessage());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        AuthRequest req = new AuthRequest("unauthorized");

        when(passwordEncoder.matches(req.getPassword(), ADMIN_PASSWORD))
                .thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> underTest.login(req));

        verify(passwordEncoder).matches(req.getPassword(), ADMIN_PASSWORD);
    }

    @Test
    void login() {
        AuthRequest req = new AuthRequest("authorized");
        when(passwordEncoder.matches(req.getPassword(), ADMIN_PASSWORD))
                .thenReturn(true);
        underTest.login(req);
        verify(passwordEncoder).matches(req.getPassword(), ADMIN_PASSWORD);
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

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken("Basic"));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken("Bearer"));
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        String token = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION * -1);

        assertThrows(
                ExpiredJwtException.class,
                () -> underTest.validateToken(
                        String.format("Bearer %s", token)));
    }

    @Test
    void validateToken() {
        String token = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION);

        underTest.validateToken(String.format("Bearer %s", token));
    }
}
