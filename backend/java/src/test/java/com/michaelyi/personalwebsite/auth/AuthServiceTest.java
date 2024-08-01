package com.michaelyi.personalwebsite.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
                passwordEncoder
        );
    }

    @Test
    void willThrowLoginWhenBadRequest() {
        AuthRequest nullReq = new AuthRequest(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(nullReq)
        );

        AuthRequest emptyReq = new AuthRequest("");

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(emptyReq)
        );

        AuthRequest blankReq = new AuthRequest(" ");

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.login(blankReq)
        );

        Mockito.verifyNoInteractions(passwordEncoder);
    }

    @Test
    void willThrowLoginWhenUnauthorized() {
        AuthRequest req = new AuthRequest("unauthorized");

        Mockito.when(
                passwordEncoder.matches(req.password(), ADMIN_PASSWORD)
        ).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> underTest.login(req));

        Mockito.verify(passwordEncoder).matches(req.password(), ADMIN_PASSWORD);
    }

    @Test
    void login() {
        AuthRequest req = new AuthRequest("authorized");

        Mockito.when(
                passwordEncoder.matches(req.password(), ADMIN_PASSWORD)
        ).thenReturn(true);

        underTest.login(req);

        Mockito.verify(passwordEncoder).matches(req.password(), ADMIN_PASSWORD);
    }

    @Test
    void willThrowValidateTokenWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken("")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken(" ")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken("Basic")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.validateToken("Bearer")
        );
    }

    @Test
    void willThrowValidateTokenWhenTokenIsInvalid() {
        String token = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION * -1
        );

        assertThrows(
                UnauthorizedException.class,
                () -> underTest.validateToken(String.format("Bearer %s", token))
        );
    }

    @Test
    void validateToken() {
        String token = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION
        );

        underTest.validateToken(String.format("Bearer %s", token));
    }
}
