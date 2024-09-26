package com.michaelyi.personalwebsite.util;

public class DateUtil {
    private static final int MS_PER_SECOND = 1000;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;
    
    public static long getTotalHours(long ms) {
        return ms / (MS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR);
    }

    public static long getRemainingMinutes(long ms) {
        return (ms / (MS_PER_SECOND * SECONDS_PER_MINUTE))
                % MINUTES_PER_HOUR;
    }

    public static long getRemainingSeconds(long ms) {
        return (ms / MS_PER_SECOND) % SECONDS_PER_MINUTE;
    }

    public static long getRemainingMillis(long ms) {
        return ms % MS_PER_SECOND;
    }
}
