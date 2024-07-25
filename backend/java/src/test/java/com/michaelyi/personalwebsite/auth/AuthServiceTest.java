package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService service;

    @Mock
    private JwtService jwtService;

    private static final BCryptPasswordEncoder ENCODER
            = new BCryptPasswordEncoder();
    private static final String AUTHORIZED_PASSWORD = "authorized password";
    private static final String UNAUTHORIZED_PASSWORD = "unauthorized password";
    private static final String BEARER_TOKEN = "Bearer token";
    private static final String TOKEN = "token";
    private static final String ENCODED_AUTHORIZED_PASSWORD
            = ENCODER.encode(AUTHORIZED_PASSWORD);
    private static final User ADMIN_USER
            = new User(ENCODED_AUTHORIZED_PASSWORD);

    @BeforeEach
    void setUp() {
        service = new AuthService(
                ENCODED_AUTHORIZED_PASSWORD,
                jwtService,
                ADMIN_USER
        );
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        LoginRequest unauthorizedLoginRequest = new LoginRequest(
                UNAUTHORIZED_PASSWORD
        );

        assertThrows(
                UnauthorizedException.class, () ->
                        service.login(unauthorizedLoginRequest)
        );

        verifyNoInteractions(jwtService);
    }

    @Test
    void login() {
        LoginRequest authorizedLoginRequest = new LoginRequest(
                AUTHORIZED_PASSWORD
        );

        service.login(authorizedLoginRequest);

        verify(jwtService).generateToken(ADMIN_USER);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        when(jwtService.isTokenExpired(TOKEN)).thenReturn(true);

        assertThrows(
                UnauthorizedException.class,
                () -> service.validateToken(BEARER_TOKEN)
        );

        verify(jwtService).isTokenExpired(TOKEN);
    }

    @Test
    void validateToken() {
        when(jwtService.isTokenExpired(TOKEN)).thenReturn(false);

        service.validateToken(BEARER_TOKEN);

        verify(jwtService).isTokenExpired(TOKEN);
    }
}
