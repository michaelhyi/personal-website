package com.michaelyi.personalwebsite.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.michaelyi.personalwebsite.util.StringUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {
    private final String adminPassword;
    private final String signingKey;
    private final PasswordEncoder passwordEncoder;
    private static final int BEARER_PREFIX_LENGTH = 7;

    public AuthService(
            @Value("${admin.pw}") String adminPassword,
            @Value("${jwt.secret-key}") String signingKey,
            PasswordEncoder passwordEncoder) {
        this.adminPassword = adminPassword;
        this.signingKey = signingKey;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(AuthRequest req) {
        if (StringUtil.isStringInvalid(req.password())) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        boolean authorized = passwordEncoder.matches(
                req.password(),
                adminPassword);

        if (!authorized) {
            throw new UnauthorizedException("Invalid password");
        }

        Map<String, Object> claims = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(AuthUtil.ADMIN_EMAIL)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + AuthUtil.JWT_EXPIRATION))
                .signWith(
                        AuthUtil.getSigningKey(signingKey),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String authHeader) {
        if (AuthUtil.isAuthHeaderInvalid(authHeader)) {
            throw new IllegalArgumentException(
                    "Authorization header is invalid");
        }

        String token = authHeader.substring(BEARER_PREFIX_LENGTH);

        Jwts
                .parserBuilder()
                .setSigningKey(AuthUtil.getSigningKey(signingKey))
                .build()
                .parseClaimsJws(token);
    }
}
