package com.michaelyi.personalwebsite.util;

public class StringUtil {
    public static boolean isStringInvalid(String s) {
        return s == null || s.isBlank() || s.isEmpty();
    }
}
