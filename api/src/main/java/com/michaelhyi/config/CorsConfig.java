package com.michaelhyi.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Value("#{'${security.cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    private static final List<String> ALLOWED_METHODS =
        Arrays.asList("GET,POST,PUT,DELETE,OPTIONS".split(","));

    private static final List<String> ALLOWED_HEADERS =
        Arrays.asList("Authorization,Content-Type".split(","));

    private static final List<String> EXPOSED_HEADERS =
        Arrays.asList("Authorization,Content-Type".split(","));

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setExposedHeaders(EXPOSED_HEADERS);
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}