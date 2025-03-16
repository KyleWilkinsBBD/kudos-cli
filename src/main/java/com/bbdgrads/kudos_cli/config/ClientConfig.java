package com.bbdgrads.kudos_cli.config;

import org.springframework.stereotype.Component;

@Component
public class ClientConfig {

    private String ClientId;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }
}
