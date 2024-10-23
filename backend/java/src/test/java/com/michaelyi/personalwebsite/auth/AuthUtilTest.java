package com.michaelyi.personalwebsite.auth;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthUtilTest {
    @Test
    void willReturnFalseDuringIsAuthHeaderValidWhenHeaderIsNull() {
        // given
        String authHeader = null;

        // when
        boolean actual = AuthUtil.isAuthHeaderValid(authHeader);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnFalseDuringIsAuthHeaderValidWhenHeaderIsBlank() {
        // given
        String authHeader = "";

        // when
        boolean actual = AuthUtil.isAuthHeaderValid(authHeader);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnFalseDuringIsAuthHeaderValidWhenHeaderIsEmpty() {
        // given
        String authHeader = " ";

        // when
        boolean actual = AuthUtil.isAuthHeaderValid(authHeader);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnFalseDuringIsAuthHeaderValidWhenHeaderStartsWithWrongPrefix() {
        // given
        String authHeader = "Basic ";

        // when
        boolean actual = AuthUtil.isAuthHeaderValid(authHeader);

        // then
        assertFalse(actual);
    }

    @Test
    void willReturnTrueDuringIsAuthHeaderValidWhenHeaderIsValid() {
        // given
        String authHeader = "Bearer {INSERT_TOKEN_HERE}";

        // when
        boolean actual = AuthUtil.isAuthHeaderValid(authHeader);

        // then
        assertTrue(actual);
    }
}
