package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.config.AuthState;
import com.bbdgrads.kudos_cli.model.UserSession;
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


    private final AuthService authClient;
    private final AuthState authState;
    private final UserSession userSession;
    private String authCode;
    private CountDownLatch latch = new CountDownLatch(1);

    public StartupService(AuthService authClient, AuthState authState, UserSession userSession) {
        this.authClient = authClient;
        this.authState = authState;
        this.userSession = userSession;
    }

    @PostConstruct
    public void authenticationStartup() throws InterruptedException {
        listenForAuthCallback();
        boolean serverOnline = false;
        Optional<Map> clientId = Optional.empty();
        while(!serverOnline){
            clientId = authClient.fetchGoogleClientId();
            if(clientId.isEmpty()){
                System.out.println("Waiting for server response...");
                Thread.sleep(4000);
            } else{
                serverOnline= true;
            }
        }
        Map id = clientId.get();
        boolean proceed = false;
        while(!proceed){
            try {
                authClient.getAuthCodeFromUser(id.get("client_id").toString());
                latch.await();
                Optional<Map> response = authClient.sendAuthCodeToApi(authCode);
                if(response.isPresent()){
                    proceed = true;
                    authState.setAPI_KEY(response.get().get("API-KEY").toString());
                    //authClient.testAuth(response.get().get("API-KEY").toString());
                    populateUserSession(response.get().get("user"));
                    System.out.println(String.format("Welcome, %s, to kudo api! \n", userSession.getUsername()));
                } else{
                    System.err.println("Invalid Auth Code, Please Try Again...\n\n");
                    latch = new CountDownLatch(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Invalid Auth Code, Please Try Again...\n\n");
                latch = new CountDownLatch(1);
            }
        }


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

    public void populateUserSession(Object userObj){
        if (userObj instanceof Map) {
            Map<String, Object> userMap = (Map<String, Object>) userObj;

            userSession.setUserId(((Number) userMap.get("userId")).longValue()); // Convert to Long
            userSession.setUsername((String) userMap.get("username"));
            userSession.setGoogleId((String) userMap.get("googleId"));
            userSession.setTeamName((String) userMap.get("team")); // Can be null
            userSession.setAdmin((Boolean) userMap.get("admin"));
        }
    }
}
