package com.michaelyi.personalwebsite.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michaelyi.personalwebsite.status.Status.Details;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    private StatusService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StatusService();
    }

    @Test
    void getStatus() throws Exception {
        Status actual = underTest.getStatus();

        String serverStatus = actual.getServers();
        String uptime = actual.getUptime();
        Details details = actual.getDetails();

        Matcher uptimeMatcher = Pattern
                .compile("^\\d{2}h \\d{2}m \\d{2}s \\d+ms$")
                .matcher(uptime);

        assertEquals(serverStatus, "UP");
        assertTrue(uptimeMatcher.matches());

        Field[] detailsFields = details.getClass().getDeclaredFields();

        for (Field f : detailsFields) {
            f.setAccessible(true);

            ServerStatus detail = (ServerStatus) f.get(details);
            assertEquals(detail.name(), "UP");
        }
    }
}
