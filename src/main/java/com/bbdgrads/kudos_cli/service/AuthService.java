package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.config.AuthState;
import com.bbdgrads.kudos_cli.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Value("${kudos.api.base.url}")
    private String baseUrl;
    private final RequestService requestService;

    public AuthService(RequestService requestService){
        this.requestService = requestService;
    }

    public Optional<Map> fetchGoogleClientId(){
        return requestService.getRequest("/api/client_id", Map.class);
    }

    public void getAuthCodeFromUser(String clientId) throws Exception{
        String REDIRECT_URI = "http://localhost:8090/auth_code";
        String AUTH_URL = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&access_type=offline" +
                "&prompt=consent";

        if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
            Desktop.getDesktop().browse(new URI(AUTH_URL));
        } else {
            System.out.println("Open this URL to login...");
            System.out.println(AUTH_URL);
        }

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the authorisation code: ");
//
//        return scanner.nextLine().trim();
    }

    public Optional<Map> sendAuthCodeToApi(String authCode){
        return requestService.postRequest("/api/authenticate", Map.class, Map.of("authCode", authCode));
    }

    public void testAuth(String apiToken){
        Optional<String> test = requestService.getRequest("/api/test-auth", String.class);
        test.ifPresent(System.out::println);
    }
}
