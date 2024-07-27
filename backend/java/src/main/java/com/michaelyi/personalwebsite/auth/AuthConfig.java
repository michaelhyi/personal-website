package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.util.Constants;
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

import static com.michaelyi.personalwebsite.util.Constants.ALLOWED_AND_EXPOSED_HEADERS;
import static com.michaelyi.personalwebsite.util.Constants.ALLOWED_METHODS;
import static com.michaelyi.personalwebsite.util.Constants.SECURITY_AUTH_ADMIN_PW;
import static com.michaelyi.personalwebsite.util.Constants.SECURITY_CORS_ALLOWED_ORIGINS;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    private final String adminPassword;
    private final List<String> allowedOrigins;
    private final AuthFilter authFilter;

    public AuthConfig(
            @Value(SECURITY_AUTH_ADMIN_PW)
            String adminPassword,
            @Value(SECURITY_CORS_ALLOWED_ORIGINS)
            List<String> allowedOrigins,
            AuthFilter authFilter
    ) {
        this.adminPassword = adminPassword;
        this.allowedOrigins = allowedOrigins;
        this.authFilter = authFilter;
    }

    @Bean
    public User user() {
        return new User(adminPassword);
    }

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

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(Constants.AUTH_ENDPOINTS)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST)
                        .hasAnyRole(Constants.ADMIN_ROLE)
                        .requestMatchers(
                                HttpMethod.GET,
                                Constants.POST_ENDPOINTS
                        )
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT)
                        .hasAnyRole(Constants.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE)
                        .hasAnyRole(Constants.ADMIN_ROLE)
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .addFilterBefore(
                        authFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
