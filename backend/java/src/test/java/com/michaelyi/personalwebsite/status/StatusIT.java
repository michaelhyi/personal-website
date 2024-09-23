package com.michaelyi.personalwebsite.status;

import com.michaelyi.personalwebsite.IntegrationTest;
import com.michaelyi.personalwebsite.status.Status.Details;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class StatusIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getStatus() throws Exception {
        String res = mvc.perform(get("/v2/status"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        StatusResponse statusResponse = MAPPER.readValue(
                res,
                StatusResponse.class);

        assertEquals(statusResponse.getStatus().getServersAsString(), "UP");
        assertNotNull(statusResponse.getStatus().getUptime());

        Details details = statusResponse.getStatus().getDetails();
        Field[] detailsFields = details.getClass().getDeclaredFields();

        for (Field f : detailsFields) {
            f.setAccessible(true);
            String value = ((ServerStatus) f.get(details)).name();
            assertEquals(value, "UP");
        }
    }
}
