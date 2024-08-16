package com.michaelyi.personalwebsite;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.auth.AuthRequest;
import com.michaelyi.personalwebsite.auth.LoginResponse;

@Testcontainers
public abstract class IntegrationTest {
    private static final int REDIS_PORT = 6379;
    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected static final ObjectWriter WRITER = MAPPER.writer();
    protected static final String AUTHORIZED_PASSWORD = "authorized password";

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0.36");

    @Container
    private static final GenericContainer<?> REDIS = new GenericContainer<>("redis:6.2.14")
            .withExposedPorts(REDIS_PORT);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.data.redis.host", REDIS::getHost);
        registry.add(
                "spring.data.redis.port",
                () -> String.valueOf(REDIS.getMappedPort(REDIS_PORT)));
    }

    protected String getAuth(MockMvc mvc) throws Exception {
        String res = mvc.perform(post("/v2/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(WRITER.writeValueAsString(
                        new AuthRequest(AUTHORIZED_PASSWORD))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponse loginResponse = MAPPER.readValue(
            res,
            LoginResponse.class);
        String token = loginResponse.getToken();

        return String.format("Bearer %s", token);
    }
}
