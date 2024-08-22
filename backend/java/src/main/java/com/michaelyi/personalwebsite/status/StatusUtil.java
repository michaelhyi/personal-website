package com.michaelyi.personalwebsite.status;

public class StatusUtil {
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MS_PER_SECOND = 1000;

    public static long getHours(long uptime) {
        return uptime / (MS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR);
    }

    public static long getMinutes(long uptime) {
        return (uptime / (MS_PER_SECOND * SECONDS_PER_MINUTE)) % MINUTES_PER_HOUR;
    }

    public static long getSeconds(long uptime) {
        return (uptime / MS_PER_SECOND) % SECONDS_PER_MINUTE;
    }

    public static long getMilliseconds(long uptime) {
        return uptime % MS_PER_SECOND;
    }
}
