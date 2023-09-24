package com.api.gw;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    public SecurityConfig(
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/v1/auth/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/post/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/post/**")
                        .hasAnyRole("ADMIN")
                        .anyExchange()
                        .authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }
}