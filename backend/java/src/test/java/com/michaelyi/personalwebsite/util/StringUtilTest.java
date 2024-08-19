package com.michaelyi.personalwebsite.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilTest {
    @Test
    void willReturnTrueWhenStringIsNull() {
        String s = null;
        boolean actual = StringUtil.isStringInvalid(s);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenStringIsEmpty() {
        String s = "";
        boolean actual = StringUtil.isStringInvalid(s);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenStringIsBlank() {
        String s = " ";
        boolean actual = StringUtil.isStringInvalid(s);
        assertTrue(actual);
    }
}
