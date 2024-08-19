package com.michaelyi.personalwebsite.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.michaelyi.personalwebsite.util.StringUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Service
public class AuthService {
    private final String adminPassword;
    private final String jwtSecret;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            @Value("${admin.pw}") String adminPassword,
            @Value("${jwt.secret-key}") String jwtSecret,
            PasswordEncoder passwordEncoder) {
        this.adminPassword = adminPassword;
        this.jwtSecret = jwtSecret;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String password) {
        if (StringUtil.isStringInvalid(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        boolean authorized = passwordEncoder.matches(
                password,
                adminPassword);

        if (!authorized) {
            throw new UnauthorizedException("Wrong password");
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
                        AuthUtil.getSigningKey(jwtSecret),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token) {
        if (StringUtil.isStringInvalid(token)) {
            throw new IllegalArgumentException("Token cannot be empty");
        }

        Key signingKey = AuthUtil.getSigningKey(jwtSecret);

        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (IllegalArgumentException
                | UnsupportedJwtException
                | MalformedJwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SignatureException | ExpiredJwtException e) {
            throw new UnauthorizedException();
        }
    }
}
