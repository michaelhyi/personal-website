package com.michaelyi.personalwebsite.health;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HealthService {
    private static final Date START_TIME = new Date();

    public HealthResponse check() {
        Date timestamp = new Date();
        long uptime = timestamp.getTime() - START_TIME.getTime();

        long hrs = (uptime / (1000 * 60 * 60)) % 24;
        long mins = (uptime / (1000 * 60)) % 60;
        long seconds = (uptime / 1000) % 60;
        long ms = uptime % 1000;

        String formattedUptime = String.format(
                "%02dh %02dm %02ds %dms", hrs, mins, seconds, ms
        );

        return new HealthResponse(formattedUptime);
    }
}
