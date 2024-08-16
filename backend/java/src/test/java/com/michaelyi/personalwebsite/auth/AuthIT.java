package com.michaelyi.personalwebsite.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.michaelyi.personalwebsite.IntegrationTest;
import com.michaelyi.personalwebsite.util.HttpResponse;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class AuthIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void login() throws Exception {
        AuthRequest req = new AuthRequest("unauthorized password");

        String res = mvc.perform(post("/v2/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(WRITER.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponse loginResponse = MAPPER.readValue(
                res,
                LoginResponse.class);
        assertEquals("Invalid password", loginResponse.getError());

        req = new AuthRequest("authorized password");
        mvc.perform(post("/v2/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(WRITER.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateToken() throws Exception {
        String unauthorizedToken = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION);

        String res = mvc.perform(post("/v2/auth/validate-token")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        String.format("Bearer %s", unauthorizedToken))
                .servletPath("/v2/auth/validate-token"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        HttpResponse httpResponse = MAPPER.readValue(res, HttpResponse.class);
        assertEquals("Unauthorized", httpResponse.getError());

        String expiredToken = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION * -1);
        res = mvc.perform(post("/v2/auth/validate-token")
                .header(
                        "Authorization",
                        String.format("Bearer %s", expiredToken))
                .servletPath("/v2/auth/validate-token"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        httpResponse = MAPPER.readValue(res, HttpResponse.class);
        assertEquals("Unauthorized", httpResponse.getError());

        AuthRequest req = new AuthRequest("authorized password");
        res = mvc.perform(post("/v2/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(WRITER.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponse loginResponse = MAPPER.readValue(
                res,
                LoginResponse.class);
        String token = loginResponse.getToken();

        mvc.perform(post("/v2/auth/validate-token")
                .header("Authorization",
                        String.format("Bearer %s", token)))
                .andExpect(status().isNoContent());
    }
}
