package com.personalwebsite.api.token;

import com.personalwebsite.api.user.User;
import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public Token(String token, TokenType type, boolean revoked, boolean expired, User user) {
        this.token = token;
        this.type = type;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }

    public Token() {
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}