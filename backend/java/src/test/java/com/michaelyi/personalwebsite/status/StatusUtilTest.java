package com.michaelyi.personalwebsite.status;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class StatusUtilTest {
    private static final int MS_PER_SECOND = 1000;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;

    private static final long HUNDRED_EIGHTY_SEVEN_HOURS = 187
            * MS_PER_SECOND
            * SECONDS_PER_MINUTE
            * MINUTES_PER_HOUR;
    private static final long FORTY_THREE_MINUTES = 43
            * MS_PER_SECOND
            * SECONDS_PER_MINUTE;
    private static final long TWENTY_FOUR_SECONDS = 24 * MS_PER_SECOND;
    private static final long TWO_HUNDRED_THIRTY_FIVE_MILLISECONDS = 235;

    private static final long UPTIME = HUNDRED_EIGHTY_SEVEN_HOURS
            + FORTY_THREE_MINUTES
            + TWENTY_FOUR_SECONDS
            + TWO_HUNDRED_THIRTY_FIVE_MILLISECONDS;

    @Test
    void canGetHours() {
        // when
        long hours = StatusUtil.getHours(UPTIME);

        // then
        assertEquals(hours, 187);
    }

    @Test
    void canGetMinutes() {
        // when
        long minutes = StatusUtil.getMinutes(UPTIME);

        // then
        assertEquals(minutes, 43);
    }

    @Test
    void canGetSeconds() {
        // when
        long seconds = StatusUtil.getSeconds(UPTIME);

        // then
        assertEquals(seconds, 24);
    }

    @Test
    void canGetMilliseconds() {
        // when
        long ms = StatusUtil.getMilliseconds(UPTIME);

        // then
        assertEquals(ms, 235);
    }
}
