package com.michaelyi.personalwebsite.util;

public class StringUtil {
    public static boolean isStringValid(String s) {
        return s != null && !s.isBlank() && !s.isEmpty();
    }
}
