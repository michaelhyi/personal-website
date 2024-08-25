package com.michaelyi.personalwebsite.auth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class AuthUtilTest {
    @Test
    void willReturnTrueDuringIsAuthHeaderInvalidWhenHeaderIsNull() {
        // given
        String authHeader = null;

        // when
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnTrueDuringIsAuthHeaderInvalidWhenHeaderIsBlank() {
        // given
        String authHeader = "";

        // when
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnTrueDuringIsAuthHeaderInvalidWhenHeaderIsEmpty() {
        // given
        String authHeader = " ";

        // when
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnTrueDuringIsAuthHeaderInvalidWhenHeaderStartsWithWrongPrefix() {
        // given
        String authHeader = "Basic ";

        // when
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);

        // then
        assertTrue(actual);
    }

    @Test
    void willReturnFalseDuringIsAuthHeaderInvalidWhenHeaderIsValid() {
        // given
        String authHeader = "Bearer {INSERT_TOKEN_HERE}";

        // when
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);

        // then
        assertFalse(actual);
    }
}
