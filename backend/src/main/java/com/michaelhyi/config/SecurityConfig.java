package com.michaelhyi.config;

import com.michaelhyi.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private static final String ADMIN = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .headers(headers -> headers
                        .cacheControl(HeadersConfigurer
                                .CacheControlConfig::disable)
                        .frameOptions(HeadersConfigurer
                                .FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST)
                        .hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.GET)
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT)
                        .hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE)
                        .hasAnyRole(ADMIN)
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}