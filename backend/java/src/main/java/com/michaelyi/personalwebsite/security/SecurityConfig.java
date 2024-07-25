package com.michaelyi.personalwebsite.security;

import com.michaelyi.personalwebsite.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthFilter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

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
                        .requestMatchers(Constants.AUTH_ENDPOINTS)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST)
                        .hasAnyRole(Constants.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, Constants.POST_ENDPOINTS)
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
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
