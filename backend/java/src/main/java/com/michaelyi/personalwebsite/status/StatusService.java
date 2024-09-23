package com.michaelyi.personalwebsite.status;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatusService {
    private static final Date START_TIME = new Date();

    public Status getStatus() {
        Date timestamp = new Date();
        long uptime = timestamp.getTime() - START_TIME.getTime();

        long hours = StatusUtil.getHours(uptime);
        long minutes = StatusUtil.getMinutes(uptime);
        long seconds = StatusUtil.getSeconds(uptime);
        long ms = StatusUtil.getMilliseconds(uptime);

        String formattedUptime = String.format(
                "%02dh %02dm %02ds %dms", hours, minutes, seconds, ms);

        return new Status(formattedUptime);
    }
}
