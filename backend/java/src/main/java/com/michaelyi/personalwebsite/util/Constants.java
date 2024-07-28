package com.michaelyi.personalwebsite.util;

public class Constants {
    // Cache
    public static final int CACHE_TTL = 900000;

    // HTML Parsing
    public static final int CLOSING_H1_TAG_LENGTH = 5;
    public static final int OPENING_H1_TAG_LENGTH = 4;

    // Properties
    public static final String SECURITY_AUTH_ADMIN_PW
            = "${security.auth.admin-pw}";

    // REST Controller
    public static final String CONTEXT_PATH = "/v2";

    // Security
    public static final String ADMIN_EMAIL = "admin@michael-yi.com";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String GRANTED_AUTHORITY_ADMIN = "ROLE_ADMIN";
    public static final String AUTH_ENDPOINTS = CONTEXT_PATH + "/auth/**";
    public static final String POST_ENDPOINTS = CONTEXT_PATH + "/post/**";
    public static final int BEARER_PREFIX_LENGTH = 7;
    public static final long JWT_EXPIRATION = 6048000000L;
}
