package com.michaelyi.util;

import java.util.List;

public class Constants {
    // Cache
    public static final int CACHE_TTL = 900000;

    // CORS
    public static final List<String> ALLOWED_AND_EXPOSED_HEADERS =
            List.of("Authorization", "Content-Type");

    public static final List<String> ALLOWED_METHODS =
            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");

    // HTML Parsing
    public static final int CLOSING_H1_TAG_LENGTH = 5;
    public static final int OPENING_H1_TAG_LENGTH = 4;

    // Properties
    public static final String AWS_ACCESS_KEY = "${aws.access-key}";
    public static final String AWS_SECRET_KEY = "${aws.secret-key}";
    public static final String AWS_S3_BUCKET = "${aws.s3.bucket}";
    public static final String AWS_REGION = "us-west-2";
    public static final String SECURITY_AUTH_ADMIN_PW
            = "${security.auth.admin-pw}";
    public static final String SECURITY_CORS_ALLOWED_ORIGINS
            = "${security.cors.allowed-origins}";
    public static final String SECURITY_JWT_SECRET_KEY
            = "${security.jwt.secret-key}";

    // REST Controller
    public static final String API_ENDPOINT_VERSION_PREFIX 
        = "/v#{'${spring.application.version}'.split('[.]')[0]}";

    // Security 
    public static final String ADMIN_EMAIL = "admin@michael-yi.com";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String AUTH_ENDPOINTS = "/v1/auth/**";
    public static final String POST_ENDPOINTS = "/v1/post/**";
    public static final int BEARER_PREFIX_LENGTH = 7;
    public static final long JWT_EXPIRATION = 6048000000L;
}
