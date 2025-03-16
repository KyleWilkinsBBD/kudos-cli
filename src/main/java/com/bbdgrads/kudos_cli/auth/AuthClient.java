package com.bbdgrads.kudos_cli.auth;

import com.bbdgrads.kudos_cli.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AuthClient {

    private final WebClient webClient;
    private final ClientConfig config;
    private final String baseUrl = "http://localhost:8080";

    @Autowired
    public AuthClient(WebClient.Builder webClientBuilder, ClientConfig config){
        this.webClient = webClientBuilder.build();
        this.config = config;
    }

    public Optional<Map> fetchGoogleClientId(){
        Map clientId = webClient.get()
                .uri(baseUrl + "/api/client_id")
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return Optional.ofNullable(clientId);
    }

    public String getAuthCodeFromUser(String clientId) throws Exception{
        String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
        String AUTH_URL = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&access_type=offline" +
                "&prompt=consent";

        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().browse(new URI(AUTH_URL));
        } else {
            System.out.println("Open this URL to login...");
            System.out.println(AUTH_URL);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the authorisation code: ");

        return scanner.nextLine().trim();
    }

    public Map sendAuthCodeToApi(String authCode){
        return webClient.post()
                .uri(baseUrl + "/api/authenticate")
                .bodyValue(Map.of("authCode", authCode))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public void testAuth(String apiToken){
        String test = webClient.get()
                .uri(baseUrl + "/api/test-auth")
                .header("bearer", apiToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(test);
    }
}
