package com.michaelyi.personalwebsite.util;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {
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

    private static final long MS = HUNDRED_EIGHTY_SEVEN_HOURS
            + FORTY_THREE_MINUTES
            + TWENTY_FOUR_SECONDS
            + TWO_HUNDRED_THIRTY_FIVE_MILLISECONDS;

    @Test
    void canGetTotalHours() {
        // when
        long hours = DateUtil.getTotalHours(MS);

        // then
        assertEquals(hours, 187);
    }

    @Test
    void canGetRemainingMinutes() {
        // when
        long minutes = DateUtil.getRemainingMinutes(MS);

        // then
        assertEquals(minutes, 43);
    }

    @Test
    void canGetRemainingSeconds() {
        // when
        long seconds = DateUtil.getRemainingSeconds(MS);

        // then
        assertEquals(seconds, 24);
    }

    @Test
    void canGetRemainingMillis() {
        // when
        long ms = DateUtil.getRemainingMillis(MS);

        // then
        assertEquals(ms, 235);
    }
}
