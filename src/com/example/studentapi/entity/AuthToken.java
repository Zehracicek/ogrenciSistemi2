package com.example.studentapi.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class AuthToken {
    @Id
    private String token;

    @ManyToOne
    private User user;

    private Instant expiresAt;

    public AuthToken() {}

    public AuthToken(String token, User user, Instant expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}

