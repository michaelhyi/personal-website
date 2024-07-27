package com.michaelyi.personalwebsite.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.TestConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-it.properties")
class AuthIT extends TestConfig {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    private ObjectWriter writer;
    private static final String EXPIRED_TOKEN = String.format(
            "%s%s%s%s",
            "eyJhbGciOiJIUzI1NiJ9",
            ".eyJzdWIiOiJ0ZXN0QG1haWwuY29tIiwiaWF0IjoxNzA5MzI0OTUxLCJleHA",
            "iOjE3MDkzMjQ5NjF9",
            ".0kgPiP5MELw6Pq6i9tJMXDxDy7n4Eu-LprqHOD4O2QM"
    );

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
        String unauthorizedToken = generateUnauthorizedToken();
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

        error = mvc.perform(get("/v2/auth/validate-token")
                        .header(
                                "Authorization",
                                String.format("Bearer %s", EXPIRED_TOKEN)
                        )
                        .servletPath("/v2/auth/validate-token"))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResolvedException()
                .getMessage();

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

    private String generateUnauthorizedToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        byte[] keyBytes = Decoders
                .BASE64
                .decode(
                        "fakesigninkeyfakesigninkeyfakesigninkeyfakesigninkey"
                );

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject("unauthorized@michael-yi.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        System.currentTimeMillis() + 1L))
                .signWith(
                        Keys.hmacShaKeyFor(keyBytes),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }
}
