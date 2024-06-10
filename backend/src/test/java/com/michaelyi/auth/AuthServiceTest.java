package com.michaelyi.auth;

import com.michaelyi.security.JwtService;
import com.michaelyi.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private JwtService jwtService;
    private AuthService underTest;
    private static User adminUser;
    private static final String AUTHORIZED_PASSWORD = "authorized password";
    private static final String UNAUTHORIZED_PASSWORD = "unauthorized password";
    private static final String BEARER_TOKEN = "Bearer token";
    private static final String TOKEN = "token";

    @BeforeAll
    static void beforeAll() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        adminUser = new User(encoder.encode(AUTHORIZED_PASSWORD));
    }

    @BeforeEach
    void setUp() {
        underTest = new AuthService(AUTHORIZED_PASSWORD, jwtService, adminUser);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        assertThrows(UnauthorizedException.class, () ->
                underTest.login(new LoginRequest(UNAUTHORIZED_PASSWORD)));
        verifyNoInteractions(jwtService);
    }

    @Test
    void login() {
        underTest.login(new LoginRequest(AUTHORIZED_PASSWORD));
        verify(jwtService).generateToken(adminUser);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        when(jwtService.isTokenExpired(TOKEN)).thenReturn(true);

        assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(BEARER_TOKEN)
        );

        verify(jwtService).isTokenExpired(TOKEN);
    }

    @Test
    void validateToken() {
        when(jwtService.isTokenExpired(TOKEN)).thenReturn(false);
        underTest.validateToken(BEARER_TOKEN);
        verify(jwtService).isTokenExpired(TOKEN);
    }
}
