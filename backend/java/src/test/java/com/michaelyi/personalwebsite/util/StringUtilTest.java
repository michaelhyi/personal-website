package com.michaelyi.personalwebsite.util;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {
    @Test
    void willReturnFalseDuringIsStringValidWhenStringIsNull() {
        // given
        String s = null;

        // when
        boolean actual = StringUtil.isStringValid(s);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnFalseDuringIsStringValidWhenStringIsBlank() {
        // given
        String s = "";

        // when
        boolean actual = StringUtil.isStringValid(s);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnFalseDuringIsStringValidWhenStringIsEmpty() {
        // given
        String s = " ";

        // when
        boolean actual = StringUtil.isStringValid(s);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnTrueDuringIsStringValidWhenStringIsValid() {
        // given
        String s = "Hello, World!";

        // when
        boolean actual = StringUtil.isStringValid(s);

        // then
        assertTrue(actual);
    }
}
