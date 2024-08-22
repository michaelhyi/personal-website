package com.michaelyi.personalwebsite.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String jwtSecret;
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    public JwtService(@Value("${jwt.secret-key}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public SecretKey getSigningKey() {
        byte[] secretBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String generateToken() {
        long currTime = System.currentTimeMillis();
        SecretKey signingKey = getSigningKey();

        return Jwts
                .builder()
                .subject(AuthUtil.ADMIN_EMAIL)
                .expiration(new Date(currTime + JWT_EXPIRATION))
                .signWith(signingKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            SecretKey signingKey = getSigningKey();

            Jwts
                    .parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (IllegalArgumentException | UnsupportedJwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (JwtException e) {
            throw new UnauthorizedException();
        }
    }
}
