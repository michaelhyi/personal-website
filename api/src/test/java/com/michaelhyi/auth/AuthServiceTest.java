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

import com.michaelhyi.exception.UnauthorizedUserException;
import com.michaelhyi.exception.UserNotFoundException;
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
    private static final String email = "test@mail.com";

    @BeforeEach
    void setUp() {
        underTest = new AuthService(repository, jwtService, List.of(email));
    }

    @Test
    void willAuthenticateWhenUserAlreadyExists() {
        User user = new User(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        underTest.authenticate(email);
        verify(repository).findByEmail(email);
        verify(jwtService).generateToken(user);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void willThrowAuthenticateWhenUnauthorizedUser() {
        assertThrows(UnauthorizedUserException.class, () -> underTest.authenticate("unauthorized@mail.com"));
        verify(repository).findByEmail("unauthorized@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(jwtService);
    }

    @Test
    void authenticate() {
        underTest.authenticate("test@mail.com");

        verify(repository).findByEmail(email);
        verify(repository).save(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    void willThrowValidateTokenWhenUserNotFound() {
        when(jwtService.extractUsername("token")).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> underTest.validateToken("token"));
        verify(jwtService).extractUsername("token");
        verify(repository).findByEmail(email);
        verifyNoMoreInteractions(jwtService);
    }

    @Test
    void validateToken() {
        User user = new User(email);
        when(jwtService.extractUsername("token")).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        underTest.validateToken("token");

        verify(jwtService).extractUsername("token");
        verify(repository).findByEmail(email);
        verify(jwtService).isTokenValid("token", user);
    }
}