// package com.michaelhyi.unit.service;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.verifyNoInteractions;
// import static org.mockito.Mockito.verifyNoMoreInteractions;
// import static org.mockito.Mockito.when;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.michaelhyi.dao.UserRepository;
// import com.michaelhyi.entity.User;
// import com.michaelhyi.exception.UnauthorizedUserException;
// import com.michaelhyi.exception.UserNotFoundException;
// import com.michaelhyi.service.AuthService;
// import com.michaelhyi.service.JwtService;

// @ExtendWith(MockitoExtension.class)
// class AuthServiceTest {
//     @Mock
//     private UserRepository repository;

//     @Mock
//     private JwtService jwtService;
//     private AuthService underTest;
//     private static final String email = "test@mail.com";

//     @BeforeEach
//     void setUp() {
//         underTest = new AuthService(repository, jwtService, List.of(email));
//     }

//     @Test
//     void willAuthenticateWhenUserAlreadyExists() {
//         User user = new User(email);

//         when(repository.findById(email)).thenReturn(Optional.of(user));

//         underTest.login(email);
//         verify(repository).findById(email);
//         verify(jwtService).generateToken(user);
//         verifyNoMoreInteractions(repository);
//         verifyNoMoreInteractions(jwtService);
//     }

//     @Test
//     void willThrowAuthenticateWhenUnauthorizedUser() {
//         assertThrows(UnauthorizedUserException.class, () -> underTest.login("unauthorized@mail.com"));
//         verify(repository).findById("unauthorized@mail.com");
//         verifyNoMoreInteractions(repository);
//         verifyNoInteractions(jwtService);
//     }

//     @Test
//     void authenticate() {
//         underTest.login("test@mail.com");

//         verify(repository).findById(email);
//         verify(repository).save(any());
//         verify(jwtService).generateToken(any());
//     }

//     @Test
//     void willThrowValidateTokenWhenUserNotFound() {
//         when(jwtService.extractUsername("token")).thenReturn(email);
//         when(repository.findById(email)).thenReturn(Optional.empty());

//         assertThrows(UserNotFoundException.class, () -> underTest.validateToken("token"));
//         verify(jwtService).extractUsername("token");
//         verify(repository).findById(email);
//         verifyNoMoreInteractions(jwtService);
//     }

//     @Test
//     void validateToken() {
//         User user = new User(email);

//         when(jwtService.extractUsername("token")).thenReturn(email);
//         when(repository.findById(email)).thenReturn(Optional.of(user));

//         underTest.validateToken("token");

//         verify(jwtService).extractUsername("token");
//         verify(repository).findById(email);
//         verify(jwtService).isTokenValid("token", user);
//     }
// }