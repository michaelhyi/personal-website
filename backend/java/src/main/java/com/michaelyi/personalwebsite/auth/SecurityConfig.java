package com.michaelyi.personalwebsite.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final List<String> allowedOrigins;
    private final AuthFilter authFilter;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    private static final List<String> ALLOWED_AND_EXPOSED_HEADERS = List.of(
            "Authorization",
            "Content-Type"
    );
    private static final List<String> ALLOWED_METHODS = List.of(
            "GET",
            "POST",
            "PATCH",
            "DELETE",
            "OPTIONS"
    );
    private static final String[] WHITELISTED_ENDPOINTS = {
            "/",
            "/v2/auth/login",
            "/v2/auth/validate-token"
    };

    public SecurityConfig(
            @Value("${cors.allowed-origins}") List<String> allowedOrigins,
            AuthFilter authFilter,
            AuthenticationEntryPointImpl authenticationEntryPoint
    ) {
        this.allowedOrigins = allowedOrigins;
        this.authFilter = authFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(ALLOWED_METHODS);
        config.setAllowedHeaders(ALLOWED_AND_EXPOSED_HEADERS);
        config.setExposedHeaders(ALLOWED_AND_EXPOSED_HEADERS);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(WHITELISTED_ENDPOINTS)
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/v2/post/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        sess -> sess.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        authFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exc -> exc
                        .authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }
}
