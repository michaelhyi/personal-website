package com.michaelyi.personalwebsite.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final String signingKey;
    private final long jwtExpiration;

    public JwtService(
            @Value("${jwt.signing-key}") String signingKey,
            @Value("${jwt.expiration}") long jwtExpiration
    ) {
        this.signingKey = signingKey;
        this.jwtExpiration = jwtExpiration;
    }

    public SecretKey getSigningKey() {
        byte[] secretBytes = Decoders.BASE64.decode(signingKey);
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
