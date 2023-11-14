package com.personalwebsite.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .requiresChannel(channel -> channel
                        .requestMatchers(r -> r
                                .getHeader("X-Forwarded-Proto")
                                != null))
                .requiresChannel(channel -> channel
                        .anyRequest()
                        .requiresSecure())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository
                                .withHttpOnlyFalse()))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/api/v1/user/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/post/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}