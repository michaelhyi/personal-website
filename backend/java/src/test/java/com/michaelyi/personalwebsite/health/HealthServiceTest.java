package com.michaelyi.personalwebsite.health;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class HealthServiceTest {
    private HealthService service;

    @BeforeEach
    void setUp() {
        service = new HealthService();
    }

    @Test
    void check() throws Exception {
        HealthResponse res = service.check();

        assertEquals(res.getStatus(), "UP");
        assertNotNull(res.getUptime());

        Field[] detailsFields = res.getDetails().getClass().getDeclaredFields();
        for (Field f : detailsFields) {
            f.setAccessible(true);
            String value = ((HealthStatus) f.get(res.getDetails())).name();

            assertEquals(value, "UP");
        }
    }
}
