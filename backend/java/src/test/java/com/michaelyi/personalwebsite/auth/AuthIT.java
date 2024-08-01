package com.michaelyi.personalwebsite.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class AuthIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    private ObjectWriter writer;

    @BeforeEach
    void setUp() {
        writer = mapper.writer();
    }

    @Test
    void login() throws Exception {
        AuthRequest req = new AuthRequest("unauthorized password");

        String error = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Unauthorized", error);

        req = new AuthRequest("authorized password");
        String res = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateToken() throws Exception {
        String unauthorizedToken = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION
        );
        String error = mvc.perform(get("/v2/auth/validate-token")
                        .header(
                                "Authorization",
                                String.format("Bearer %s", unauthorizedToken)
                        )
                        .servletPath("/v2/auth/validate-token"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("Unauthorized", error);

        String expiredToken = AuthTestUtil.generateToken(
                AuthUtil.JWT_EXPIRATION * -1
        );
        error = mvc.perform(get("/v2/auth/validate-token")
                        .header(
                                "Authorization",
                                String.format("Bearer %s", expiredToken)
                        )
                        .servletPath("/v2/auth/validate-token"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("Unauthorized", error);

        AuthRequest req = new AuthRequest("authorized password");
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mvc.perform(get("/v2/auth/validate-token")
                        .header("Authorization",
                                String.format("Bearer %s", token)))
                .andExpect(status().isOk());
    }
}
