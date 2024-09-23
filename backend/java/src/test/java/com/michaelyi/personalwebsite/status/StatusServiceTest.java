package com.michaelyi.personalwebsite.status;

import com.michaelyi.personalwebsite.status.Status.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        ServerStatus serverStatus = actual.getServers();
        String uptime = actual.getUptime();
        Details details = actual.getDetails();

        Matcher uptimeMatcher = Pattern
                .compile("^\\d{2}h \\d{2}m \\d{2}s \\d+ms$")
                .matcher(uptime);

        assertEquals(ServerStatus.UP, serverStatus);
        assertTrue(uptimeMatcher.matches());

        Field[] detailsFields = details.getClass().getDeclaredFields();

        for (Field f : detailsFields) {
            f.setAccessible(true);

            ServerStatus detailStatus = (ServerStatus) f.get(details);
            assertEquals(ServerStatus.UP, detailStatus);
        }
    }
}
