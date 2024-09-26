package com.michaelyi.personalwebsite.health;

import com.michaelyi.personalwebsite.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class HealthIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getHealth() throws Exception {
        MockHttpServletResponse res = mvc.perform(get("/"))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), res.getStatus());
        HealthResponse statusResponse = MAPPER.readValue(
                res.getContentAsString(),
                HealthResponse.class
        );

        assertEquals(statusResponse.getHealth().getServers(), "UP");

        Matcher uptimeMatcher = Pattern
                .compile("^\\d{2}h \\d{2}m \\d{2}s \\d+ms$")
                .matcher(statusResponse.getHealth().getUptime());
        assertTrue(uptimeMatcher.matches());

        assertEquals(statusResponse.getHealth().getMysql(), "UP");
        assertEquals(statusResponse.getHealth().getRedis(), "UP");
    }
}
