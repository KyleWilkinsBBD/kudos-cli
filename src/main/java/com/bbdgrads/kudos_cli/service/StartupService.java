package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.auth.AuthClient;
import com.bbdgrads.kudos_cli.config.AuthState;
import com.bbdgrads.kudos_cli.config.ClientConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Service
public class StartupService {

    private final AuthClient authClient;
    private final AuthState authState;

    public StartupService(AuthClient authClient, ClientConfig config, AuthState authState) {
        this.authClient = authClient;
        this.authState = authState;
    }

    @PostConstruct
    public void fetchClientIdAtStartup(){
        Optional<Map> clientId = authClient.fetchGoogleClientId();
        clientId.ifPresent(map -> {
            boolean proceed = false;
            while(!proceed){
                try {
                    String authCode = authClient.getAuthCodeFromUser(map.get("client_id").toString());
                    Map response = authClient.sendAuthCodeToApi(authCode);
                    if(response.containsKey("API-KEY")){
                        proceed = true;
                        authClient.testAuth(response.get("API-KEY").toString());
                    }
                } catch (Exception e) {
                    System.err.println("Invalid Auth Code, Please Try Again...\n\n");
                }
            }

        });

    }
}
