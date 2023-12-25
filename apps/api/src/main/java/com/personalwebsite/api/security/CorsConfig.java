package com.personalwebsite.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Value("#{'${security.cors.allowed-headers}'.split(',')}")
    private List<String> ALLOWED_HEADERS;

    @Value("#{'${security.cors.allowed-methods}'.split(',')}")
    private List<String> ALLOWED_METHODS;

    @Value("#{'${security.cors.allowed-origins}'.split(',')}")
    private List<String> ALLOWED_ORIGINS;

    @Value("#{'${security.cors.exposed-headers}'.split(',')}")
    private List<String> EXPOSED_HEADERS;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setExposedHeaders(EXPOSED_HEADERS);
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}