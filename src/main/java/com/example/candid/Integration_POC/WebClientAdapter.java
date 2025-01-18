package com.example.candid.Integration_POC;

//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Component
public class WebClientAdapter {

    private final WebClient webClient;
    public WebClientAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build(); // Adjust base URL as needed
    }


//    public WebClientAdapter(@LoadBalanced WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.
//                baseUrl("http://localhost:8085").build(); // Adjust base URL as needed
//    }

    public <T> Mono<T> makeRequest(HttpMethod method, String uri, Object requestBody,
                                   ParameterizedTypeReference<T> responseType, String apiKey, boolean isList) {

        WebClient.RequestBodySpec requestBodySpec = webClient.method(method)
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey);

        if (requestBody != null) {
            requestBodySpec = (WebClient.RequestBodySpec) requestBodySpec.bodyValue(requestBody); // Add body for POST/PUT requests
        }

        if (isList) {
            return (Mono<T>) requestBodySpec.retrieve()
                    .bodyToFlux(responseType)
                    .collectList()  // Collects all the items in a list
                    .onErrorResume(e -> {
                        if (e instanceof java.net.ConnectException || e instanceof java.net.SocketTimeoutException) {
                            // Trigger the circuit breaker
                            return Mono.error(e);  // Rethrow the error so the circuit breaker can catch it
                        }
                        System.err.println("Error making request: " + e.getMessage());
                        return Mono.empty();
                    });
        } else {
            return requestBodySpec.retrieve()
                    .bodyToMono(responseType)
                    .onErrorResume(e -> {
                        if (e instanceof java.net.ConnectException || e instanceof java.net.SocketTimeoutException) {
                            // Trigger the circuit breaker
                            return Mono.error(e);  // Rethrow the error so the circuit breaker can catch it
                        }
                        System.err.println("Error making request: " + e.getMessage());
                        return Mono.empty();
                    });
        }
    }
}
