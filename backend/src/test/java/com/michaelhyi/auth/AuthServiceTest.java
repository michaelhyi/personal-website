package com.michaelhyi.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michaelhyi.security.JwtService;
import com.michaelhyi.user.User;
import com.michaelhyi.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private JwtService jwtService;
    private AuthService underTest;
    private static final String EMAIL = "test@mail.com";
    private static final String UNAUTHORIZED_EMAIL = "unauthorized@mail.com";
    private static final String BEARER_TOKEN = "Bearer token";
    private static final String TOKEN = "token";

    @BeforeEach
    void setUp() {
        underTest = new AuthService(List.of(EMAIL), repository, jwtService);
    }

    @Test
    void willLoginWhenUserAlreadyExists() {
        User user = new User(EMAIL);

        when(repository.findById(EMAIL)).thenReturn(Optional.of(user));

        underTest.login(EMAIL);
        verify(repository).findById(EMAIL);
        verify(jwtService).generateToken(user);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        assertThrows(UnauthorizedUserException.class, () -> underTest.login(UNAUTHORIZED_EMAIL));
        verify(repository).findById(UNAUTHORIZED_EMAIL);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(jwtService);
    }

    @Test
    void login() {
        underTest.login(EMAIL);

        verify(repository).findById(EMAIL);
        verify(repository).save(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    void willThrowValidateTokenWhenUserNotFound() {
        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(repository.findById(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> underTest.validateToken(BEARER_TOKEN));
        verify(jwtService).extractUsername(TOKEN);
        verify(repository).findById(EMAIL);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        User user = new User(EMAIL);

        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(repository.findById(EMAIL)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(TOKEN, user)).thenReturn(false);

        assertThrows(UnauthorizedUserException.class, () -> underTest.validateToken(BEARER_TOKEN));
        verify(jwtService).extractUsername(TOKEN);
        verify(repository).findById(EMAIL);
        verify(jwtService).isTokenValid(TOKEN, user);
    }

    @Test
    void validateToken() {
        User user = new User(EMAIL);

        when(jwtService.extractUsername(TOKEN)).thenReturn(EMAIL);
        when(repository.findById(EMAIL)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(TOKEN, user)).thenReturn(true);

        underTest.validateToken(BEARER_TOKEN);

        verify(jwtService).extractUsername(TOKEN);
        verify(repository).findById(EMAIL);
        verify(jwtService).isTokenValid(TOKEN, user);
    }
}