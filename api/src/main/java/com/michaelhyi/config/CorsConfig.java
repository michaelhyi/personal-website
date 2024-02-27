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
    @Value("${security.cors.allowed-origins}")
    private List<String> allowedOrigins;

    private static final List<String> ALLOWED_METHODS =
            Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS");

    private static final List<String> ALLOWED_AND_EXPOSED_HEADERS =
            Arrays.asList("Authorization",
                          "Cache-Control",
                          "Content-Type",
                          "Pragma",
                          "Expires");

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(ALLOWED_METHODS);
        config.setAllowedHeaders(ALLOWED_AND_EXPOSED_HEADERS);
        config.setExposedHeaders(ALLOWED_AND_EXPOSED_HEADERS);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}