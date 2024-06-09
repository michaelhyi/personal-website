package com.michaelyi.auth;

import com.michaelyi.security.JwtService;
import com.michaelyi.user.User;
import com.michaelyi.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserDao dao;

    @Mock
    private JwtService jwtService;
    private AuthService underTest;
    private static final String EMAIL = "test@mail.com";
    private static final String UNAUTHORIZED_EMAIL = "unauthorized@mail.com";
    private static final String BEARER_TOKEN = "Bearer token";
    private static final String TOKEN = "token";

    @BeforeEach
    void setUp() {
        underTest = new AuthService(List.of(EMAIL), dao, jwtService);
    }

    @Test
    void willLoginWhenUserAlreadyExists() {
        User user = new User(EMAIL);

        when(dao.readUser(EMAIL)).thenReturn(Optional.of(user));

        underTest.login(EMAIL);
        verify(dao).readUser(EMAIL);
        verify(jwtService).generateToken(user);
        verifyNoMoreInteractions(dao);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        assertThrows(UnauthorizedException.class, () ->
                underTest.login(UNAUTHORIZED_EMAIL));
        verify(dao).readUser(UNAUTHORIZED_EMAIL);
        verifyNoMoreInteractions(dao);
        verifyNoInteractions(jwtService);
    }

    @Test
    void login() {
        underTest.login(EMAIL);

        verify(dao).readUser(EMAIL);
        verify(dao).createUser(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    void willThrowValidateTokenWhenUserNotFound() {
        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(dao.readUser(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> underTest.validateToken(BEARER_TOKEN));
        verify(jwtService).extractUsername(TOKEN);
        verify(dao).readUser(EMAIL);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        User user = new User(EMAIL);

        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(dao.readUser(EMAIL)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(TOKEN, user)).thenReturn(false);

        assertThrows(UnauthorizedException.class,
                () -> underTest.validateToken(BEARER_TOKEN));
        verify(jwtService).extractUsername(TOKEN);
        verify(dao).readUser(EMAIL);
        verify(jwtService).isTokenValid(TOKEN, user);
    }

    @Test
    void validateToken() {
        User user = new User(EMAIL);

        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(dao.readUser(EMAIL)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(TOKEN, user)).thenReturn(true);

        underTest.validateToken(BEARER_TOKEN);

        verify(jwtService).extractUsername(TOKEN);
        verify(dao).readUser(EMAIL);
        verify(jwtService).isTokenValid(TOKEN, user);
    }
}
