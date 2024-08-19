package com.michaelyi.personalwebsite.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.michaelyi.personalwebsite.IntegrationTest;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class AuthIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsNull() throws Exception {
        AuthRequest req = new AuthRequest(null);
        MockHttpServletResponse res = AuthTestUtil.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatus());
        assertEquals("Password cannot be empty", getError(res));
    }

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsEmpty() throws Exception {
        AuthRequest req = new AuthRequest("");
        MockHttpServletResponse res = AuthTestUtil.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatus());
        assertEquals("Password cannot be empty", getError(res));
    }

    @Test
    void willThrowBadRequestDuringLoginWhenPasswordIsBlank() throws Exception {
        AuthRequest req = new AuthRequest(" ");
        MockHttpServletResponse res = AuthTestUtil.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatus());
        assertEquals("Password cannot be empty", getError(res));
    }

    @Test
    void willThrowUnauthorizedDuringLoginWhenWrongPassword() throws Exception {
        AuthRequest req = new AuthRequest("unauthorized password");
        MockHttpServletResponse res = AuthTestUtil.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Invalid password", getError(res));
    }

    @Test
    void willGenerateTokenDuringLoginWhenAuthorized() throws Exception {
        AuthRequest req = new AuthRequest("authorized password");
        MockHttpServletResponse res = AuthTestUtil.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.OK.value(), res.getStatus());
    }

    @Test
    void willThrowUnauthorizedDuringValidateTokenWhenTokenUsesWrongKey()
            throws Exception {
        String token = AuthTestUtil.generateBadToken(
                AuthUtil.JWT_EXPIRATION);
        MockHttpServletResponse res = AuthTestUtil.validateToken(
                token,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Unauthorized", getError(res));
    }

    @Test
    void willThrowUnauthorizedDuringValidateTokenWhenTokenExpired()
            throws Exception {
        String token = AuthTestUtil.generateBadToken(
                AuthUtil.JWT_EXPIRATION * -1);
        MockHttpServletResponse res = AuthTestUtil.validateToken(
                token,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Unauthorized", getError(res));

    }

    @Test
    void validatetokenWillSucceedOnAuthorizedToken() throws Exception {
        String auth = AuthTestUtil.getAuth(mvc, MAPPER, WRITER);
        String token = auth.replace("Bearer ", "");
        MockHttpServletResponse res = AuthTestUtil.validateToken(token, mvc, MAPPER, WRITER);
        String resJson = res.getContentAsString();
        assertEquals(HttpStatus.NO_CONTENT.value(), res.getStatus());
        assertEquals("{}", resJson);
    }
}
