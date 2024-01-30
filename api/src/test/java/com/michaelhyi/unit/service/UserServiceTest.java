package com.michaelhyi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.entity.User;
import com.michaelhyi.exception.UserNotFoundException;
import com.michaelhyi.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(repository);
    }

    @Test
    void willThrowReadUserByEmailOnIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> underTest.readUserByEmail(null));
        verify(repository, never()).findByEmail(any());

        assertThrows(IllegalArgumentException.class, () -> underTest.readUserByEmail(""));
        verify(repository, never()).findByEmail(any());

        assertThrows(IllegalArgumentException.class, () -> underTest.readUserByEmail("null"));
        verify(repository, never()).findByEmail(any());

        assertThrows(IllegalArgumentException.class, () -> underTest.readUserByEmail("undefined"));
        verify(repository, never()).findByEmail(any());
    }

    @Test
    void willThrowReadUserByEmailWhenUserNotFound() {
        when(repository.findByEmail("test@mail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> underTest.readUserByEmail("test@mail.com"));
        verify(repository).findByEmail("test@mail.com");
    }

    @Test
    void readUserByEmail() {
        User user = User.builder()
                        .email("test@mail.com")
                        .build();

        given(repository.findByEmail(user.getUsername())).willReturn(Optional.of(user));

        assertEquals(user, underTest.readUserByEmail(user.getUsername()));
        verify(repository).findByEmail(user.getUsername());
    }
}