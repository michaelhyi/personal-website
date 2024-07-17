package com.michaelyi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.michaelyi.util.Constants.ALLOWED_AND_EXPOSED_HEADERS;
import static com.michaelyi.util.Constants.ALLOWED_METHODS;
import static com.michaelyi.util.Constants.SECURITY_CORS_ALLOWED_ORIGINS;

@Configuration
public class CorsConfig {
    @Value(SECURITY_CORS_ALLOWED_ORIGINS)
    private List<String> allowedOrigins;

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
