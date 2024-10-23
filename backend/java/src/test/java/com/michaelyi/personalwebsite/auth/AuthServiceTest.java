package com.michaelyi.personalwebsite.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService underTest;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private static final String ENCODED_ADMIN_PASSWORD = "admin";

    @BeforeEach
    void setUp() {
        underTest = new AuthService(
                ENCODED_ADMIN_PASSWORD,
                passwordEncoder,
                jwtService);
    }

    @Test
    void willThrowLoginWhenWrongPassword() {
        // given
        String password = "wrong";
        when(passwordEncoder.matches(
                password,
                ENCODED_ADMIN_PASSWORD)).thenReturn(false);

        // when
        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.login(password));

        // then
        assertEquals("Wrong password", err.getMessage());
        verify(passwordEncoder).matches(password, ENCODED_ADMIN_PASSWORD);
        verifyNoInteractions(jwtService);
    }

    @Test
    void canLogin() {
        // given
        String password = "correct";
        when(passwordEncoder.matches(password, ENCODED_ADMIN_PASSWORD))
                .thenReturn(true);

        // when
        underTest.login(password);

        // then
        verify(passwordEncoder).matches(password, ENCODED_ADMIN_PASSWORD);
        verify(jwtService).generateToken();
    }

    @Test
    void willThrowValidateTokenWhenTokenIsMalformed() {
        // given
        String token = "malformed token";
        doThrow(new IllegalArgumentException("Token cannot be malformed"))
                .when(jwtService)
                .validateToken(token);

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(token));

        // then
        assertEquals("Token cannot be malformed", err.getMessage());
        verify(jwtService).validateToken(token);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsUnauthorized() {
        // given
        String token = "unauthorized token";
        doThrow(new UnauthorizedException())
                .when(jwtService)
                .validateToken(token);

        // when
        UnauthorizedException err = assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(token));

        // then
        assertEquals("Unauthorized", err.getMessage());
        verify(jwtService).validateToken(token);
    }

    @Test
    void canValidateToken() {
        // given
        String token = "token";

        // when
        underTest.validateToken(token);

        // then
        verify(jwtService).validateToken(token);
    }
}
