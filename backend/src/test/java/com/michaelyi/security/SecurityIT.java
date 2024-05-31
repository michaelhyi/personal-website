package com.michaelyi.security;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:application-it.properties")
public class SecurityIT {
    private static final int REDIS_PORT = 6379;
    private static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.36");
    private static GenericContainer<?> redis = new GenericContainer<>("redis:6.2.14")
            .withExposedPorts(REDIS_PORT);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> String.valueOf(redis.getMappedPort(REDIS_PORT)));
    }

    @Autowired
    private MockMvc mvc;
    private static final String NOT_FOUND = "Not Found";

    @BeforeAll
    static void beforeAll() {
        mysql.start();
        redis.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
        redis.stop();
    }

    @Test
    void testError() throws Exception {
        exhaustMethods("/");
        exhaustMethods("/error");
        exhaustMethods("/random-endpoint");
    }

    private void exhaustMethods(String endpoint) throws Exception {
        String error = mvc.perform(get(endpoint))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(NOT_FOUND, error);

        error = mvc.perform(post(endpoint))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(NOT_FOUND, error);

        error = mvc.perform(put(endpoint))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(NOT_FOUND, error);

        error = mvc.perform(patch(endpoint))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(NOT_FOUND, error);

        error = mvc.perform(delete(endpoint))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(NOT_FOUND, error);
    }
}
