package com.bbdgrads.kudos_cli.config;

import org.springframework.stereotype.Component;

@Component
public class AuthState {
    private boolean Authenticated = false;

    public boolean isAuthenticated() {
        return Authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        Authenticated = authenticated;
    }
}
