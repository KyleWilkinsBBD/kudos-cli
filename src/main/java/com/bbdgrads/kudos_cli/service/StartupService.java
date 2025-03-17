package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.auth.AuthClient;
import com.bbdgrads.kudos_cli.config.AuthState;
import jakarta.annotation.PostConstruct;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Service
public class StartupService {

    private final AuthClient authClient;
    private final AuthState authState;
    private String authCode;
    private CountDownLatch latch = new CountDownLatch(1);

    public StartupService(AuthClient authClient, AuthState authState) {
        this.authClient = authClient;
        this.authState = authState;
    }

    @PostConstruct
    public void authenticationStartup(){
        listenForAuthCallback();
        Optional<Map> clientId = authClient.fetchGoogleClientId();
        clientId.ifPresent(map -> {
            boolean proceed = false;
            while(!proceed){
                try {
                    authClient.getAuthCodeFromUser(map.get("client_id").toString());
                    latch.await();
                    Map response = authClient.sendAuthCodeToApi(authCode);
                    if(response.containsKey("API-KEY")){
                        proceed = true;
                        authState.setAPI_KEY(response.get("API-KEY").toString());
                        //authClient.testAuth(response.get("API-KEY").toString());
                    }
                } catch (Exception e) {
                    System.err.println("Invalid Auth Code, Please Try Again...\n\n");
                    latch = new CountDownLatch(1);
                }
            }

        });

    }

    public void listenForAuthCallback(){
        // Start a small HTTP server to listen for the auth callback
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(
                RouterFunctions.route(GET("/auth_code"), request -> {
                    authCode = request.queryParam("code").orElse(null);
                    latch.countDown();
                    return ServerResponse.ok().bodyValue("You may close this window now.");
                })
        );

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create()
                .host("localhost")
                .port(8090)
                .handle(adapter)
                .bindNow();  // Bind the server

        System.out.println("OAuth server started on http://localhost:8090/auth_code");
    }
}
