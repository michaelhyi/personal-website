package com.michaelyi.personalwebsite.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    private StatusService service;

    @BeforeEach
    void setUp() {
        service = new StatusService();
    }

    @Test
    void getStatus() throws Exception {
        Status res = service.getStatus();

        assertEquals(res.getServers(), "UP");
        assertNotNull(res.getUptime());

        Field[] detailsFields = res.getDetails().getClass().getDeclaredFields();
        for (Field f : detailsFields) {
            f.setAccessible(true);
            String value = ((ServerStatus) f.get(res.getDetails())).name();
            assertEquals(value, "UP");
        }
    }
}
