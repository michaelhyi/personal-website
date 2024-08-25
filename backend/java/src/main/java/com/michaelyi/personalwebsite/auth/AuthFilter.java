package com.michaelyi.personalwebsite.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        if (isWhitelisted(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication securityCtx = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (securityCtx == null) {
            String authHeader = request.getHeader("Authorization");

            if (!AuthUtil.isAuthHeaderValid(authHeader)) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                String token = authHeader.substring(
                        AuthUtil.BEARER_PREFIX_LENGTH);
                authService.validateToken(token);
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }

            setSecurityContext(request);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhitelisted(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.equals("/v2/auth/login")
                || path.equals("/v2/auth/validate-token")
                || path.startsWith("/v2/status")
                || (path.startsWith("/v2/post/") && method.equals("GET"));
    }

    private void setSecurityContext(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminUser,
                null,
                adminUser.getAuthorities());

        token.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
