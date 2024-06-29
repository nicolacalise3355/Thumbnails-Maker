package com.nicolacalise.ApplicationBackend.models.auth;

public class AuthResponse {

    private final String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
