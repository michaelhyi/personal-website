package com.michaelyi.personalwebsite.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthTestUtil {
    public static final String SIGNING_KEY
            = "fakesigningkeyfakesigningkeyfakesigningkeyfakesigningkey";

    public static String generateToken(long expiration) {
        Map<String, Object> claims = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(AuthUtil.ADMIN_EMAIL)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(
                        new Date(currentTime + expiration)
                )
                .signWith(
                        AuthUtil.getSigningKey(SIGNING_KEY),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }
}
