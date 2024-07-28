package com.michaelyi.personalwebsite.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class HealthIT extends IntegrationTest {
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
    void check() throws Exception {
        String res = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        HealthResponse healthResponse
                = mapper.readValue(res, HealthResponse.class);

        assertEquals(healthResponse.getStatus(), "UP");
        assertNotNull(healthResponse.getUptime());

        Field[] detailsFields = healthResponse
                .getDetails()
                .getClass()
                .getDeclaredFields();
        for (Field f : detailsFields) {
            f.setAccessible(true);
            String value = ((HealthStatus) f.get(healthResponse.getDetails()))
                    .name();

            assertEquals(value, "UP");
        }
    }
}
