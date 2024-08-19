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
        MockHttpServletResponse res = AuthTestHelper.login(
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
        MockHttpServletResponse res = AuthTestHelper.login(
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
        MockHttpServletResponse res = AuthTestHelper.login(
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
        MockHttpServletResponse res = AuthTestHelper.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Wrong password", getError(res));
    }

    @Test
    void willGenerateTokenDuringLoginWhenAuthorized() throws Exception {
        AuthRequest req = new AuthRequest("authorized password");
        MockHttpServletResponse res = AuthTestHelper.login(
                req,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.OK.value(), res.getStatus());
    }

    @Test
    void willThrowUnauthorizedDuringValidateTokenWhenTokenUsesWrongKey()
            throws Exception {
        String token = AuthTestHelper.generateToken(
                AuthTestHelper.FAKE_SIGNING_KEY,
                AuthUtil.JWT_EXPIRATION);
        MockHttpServletResponse res = AuthTestHelper.validateToken(
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
        String token = AuthTestHelper.generateToken(
                AuthTestHelper.FAKE_SIGNING_KEY,
                AuthUtil.JWT_EXPIRATION * -1);
        MockHttpServletResponse res = AuthTestHelper.validateToken(
                token,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Unauthorized", getError(res));

    }

    @Test
    void willValidateTokenWhenTokenIsAuthorized() throws Exception {
        String auth = AuthTestHelper.getAuth(mvc, MAPPER, WRITER);
        String token = auth.replace("Bearer ", "");
        MockHttpServletResponse res = AuthTestHelper.validateToken(
                token,
                mvc,
                MAPPER,
                WRITER);
        String resJson = res.getContentAsString();
        assertEquals(HttpStatus.NO_CONTENT.value(), res.getStatus());
        assertEquals("{}", resJson);
    }
}
