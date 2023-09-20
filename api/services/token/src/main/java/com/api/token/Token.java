package com.api.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Token {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType type = TokenType.BEARER;

    public boolean revoked;
    public boolean expired;
    public Long userId;

    public Token(String token, boolean revoked, boolean expired, Long userId) {
        this.token = token;
        this.revoked = revoked;
        this.expired = expired;
        this.userId = userId;
    }

    public Token() {}
}