package com.michaelyi.personalwebsite.auth;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class AuthUtilTest {
    @Test
    void willReturnTrueWhenAuthHeaderIsNull() {
        String authHeader = null;
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenAuthHeaderIsEmpty() {
        String authHeader = "";
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenAuthHeaderIsBlank() {
        String authHeader = " ";
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenAuthHeaderDoesNotStartWithBearer() {
        String authHeader = "Basic ";
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);
        assertTrue(actual);
    }

    @Test
    void willReturnTrueWhenAuthHeaderIsOnlyBearer() {
        String authHeader = "Bearer";
        boolean actual = AuthUtil.isAuthHeaderInvalid(authHeader);
        assertTrue(actual);
    }
}
