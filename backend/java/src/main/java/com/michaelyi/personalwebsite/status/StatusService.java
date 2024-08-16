package com.michaelyi.personalwebsite.status;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatusService {
    private static final Date START_TIME = new Date();
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MS_PER_SECOND = 1000;

    public Status check() {
        Date timestamp = new Date();
        long uptime = timestamp.getTime() - START_TIME.getTime();

        long hrs = (
                uptime / (MS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR)
        );
        long mins = (uptime / (MS_PER_SECOND * SECONDS_PER_MINUTE))
                % MINUTES_PER_HOUR;
        long seconds = (uptime / MS_PER_SECOND) % SECONDS_PER_MINUTE;
        long ms = uptime % MS_PER_SECOND;

        String formattedUptime = String.format(
                "%02dh %02dm %02ds %dms", hrs, mins, seconds, ms
        );

        return new Status(formattedUptime);
    }
}
