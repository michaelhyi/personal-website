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
    private final long jwtExpiration;

    public JwtService(
            @Value("${jwt.secret-key}") String jwtSecret,
            @Value("${jwt.expiration}") long jwtExpiration) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
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
                .expiration(new Date(currTime + jwtExpiration))
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
