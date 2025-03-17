package com.bbdgrads.kudos_cli.config;

import org.springframework.stereotype.Component;

@Component
public class AuthState {
    private boolean Authenticated = false;
    private String API_KEY = null;

    public boolean isAuthenticated() {
        return Authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        Authenticated = authenticated;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }
}
