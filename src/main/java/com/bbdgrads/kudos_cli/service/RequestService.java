package com.bbdgrads.kudos_cli.service;

import com.bbdgrads.kudos_cli.config.AuthState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Service
public class RequestService {

    private final WebClient webClient;
    private final AuthState authState;
    @Value("${kudos.api.base.url}")
    private String baseEndpoint;

    public RequestService(WebClient webClient, AuthState authState) {
        this.webClient = webClient;
        this.authState = authState;
    }

    public <T> Optional<T> getRequest(String url, Class<T> responseType){
        try{
            return Optional.ofNullable(webClient.get()
                    .uri(baseEndpoint + url)
                    .header("Authorization", "Bearer " + authState.getAPI_KEY())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody))))
                    .bodyToMono(responseType)
                    .block());
        } catch (Exception e){
            System.err.println("Error making GET request: " + e.getMessage());
            return Optional.empty();
        }

    }

    public <T> Optional<T> postRequest(String url, Class<T> responseType, Map<String, String> dataBody){
        try{
            return Optional.ofNullable(webClient.post()
                    .uri(baseEndpoint + url)
                    .bodyValue(dataBody)
                    .header("Authorization", "Bearer " + authState.getAPI_KEY())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody))))
                    .bodyToMono(responseType)
                    .block());
        } catch (Exception e){
            System.err.println("Error making GET request: " + e.getMessage());
            return Optional.empty();
        }
    }

    public <T> Optional<T> patchRequest(String url, Class<T> responseType, Map<String, String> dataBody){
        try{
            return Optional.ofNullable(webClient.patch()
                    .uri(baseEndpoint + url)
                    .bodyValue(dataBody)
                    .header("Authorization", "Bearer " + authState.getAPI_KEY())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody))))
                    .bodyToMono(responseType)
                    .block());
        } catch (Exception e){
            System.err.println("Error making GET request: " + e.getMessage());
            return Optional.empty();
        }
    }

    public <T> Optional<T> putRequest(String url, Class<T> responseType, Map<String, String> dataBody){
        try{
            return Optional.ofNullable(webClient.put()
                    .uri(baseEndpoint + url)
                    .bodyValue(dataBody)
                    .header("Authorization", "Bearer " + authState.getAPI_KEY())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody))))
                    .bodyToMono(responseType)
                    .block());
        } catch (Exception e){
            System.err.println("Error making GET request: " + e.getMessage());
            return Optional.empty();
        }
    }


}
