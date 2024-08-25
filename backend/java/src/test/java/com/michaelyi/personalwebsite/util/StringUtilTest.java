package com.michaelyi.personalwebsite.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilTest {
    @Test
    void willReturnTrueDuringIsStringInvalidWhenStringIsNull() {
        // given
        String s = null;

        // when
        boolean actual = StringUtil.isStringInvalid(s);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnTrueDuringIsStringInvalidWhenStringIsBlank() {
        // given
        String s = "";
        
        // when
        boolean actual = StringUtil.isStringInvalid(s);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnTrueDuringIsStringInvalidWhenStringIsEmpty() {
        // given
        String s = " ";

        // when
        boolean actual = StringUtil.isStringInvalid(s);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnFalseDuringIsStringInvalidWhenStringIsValid() {
        // given
        String s = "Hello, World!";

        // when
        boolean actual = StringUtil.isStringInvalid(s);

        // then
        assertFalse(actual);
    }
}
