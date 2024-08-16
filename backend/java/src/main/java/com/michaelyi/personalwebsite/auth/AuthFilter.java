package com.michaelyi.personalwebsite.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final AdminUser adminUser;

    public AuthFilter(
            AuthService authService,
            AdminUser adminUser) {
        this.authService = authService;
        this.adminUser = adminUser;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            String authHeader = request.getHeader("Authorization");

            if (AuthUtil.isAuthHeaderInvalid(authHeader)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(AuthUtil.BEARER_PREFIX_LENGTH);
            authService.validateToken(token);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    adminUser,
                    null,
                    adminUser.getAuthorities());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
