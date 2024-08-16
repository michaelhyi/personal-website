package com.michaelyi.personalwebsite.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.util.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final ObjectWriter writer;
    private static final String[] ENDPOINTS = {
            "/v2/status",
            "/v2/auth/login",
            "/v2/auth/validate-token",
            "/v2/post",
            "/v2/post/image"
    };

    public AuthenticationEntryPointImpl(ObjectWriter writer) {
        this.writer = writer;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        HttpResponse res = new HttpResponse();
        String path = request.getServletPath();

        if (!isEndpointExist(path)) {
            res.setError("Resource not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setError("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        String json = writer.writeValueAsString(res);

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(json);
    }

    private boolean isEndpointExist(String path) {
        for (String endpoint : ENDPOINTS) {
            if (path.equals(endpoint) || path.startsWith(endpoint + "/")) {
                return true;
            }
        }

        return false;
    }
}
