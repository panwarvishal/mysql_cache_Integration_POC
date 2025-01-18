package com.example.candid.Integration_POC;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements  UserClient {
    private final WebClientAdapter webClientAdapter;
    private final UserCacheService userCacheService;  // Inject the interface

    @Override
    @CircuitBreaker(name = "userClient", fallbackMethod = "fallbackFetchUsers")
    public Mono<List<User>> fetchUsers(String apiKey) {
        return webClientAdapter.makeRequest(
                HttpMethod.GET,
                "/api/users",
                null,
                new ParameterizedTypeReference<List<User>>() {
                },
                apiKey,
                true
        );
    }

    private Mono<List<User>> fallbackFetchUsers(String apiKey, Throwable throwable) {
        // Log error for monitoring
        System.err.println("Circuit Breaker activated for fetchUsers: " + throwable.getMessage());

        // Try to get data from the cache first
        Optional<List<User>> cachedUsers = userCacheService.getCachedUsers();
        if (cachedUsers.isPresent()) {
            System.out.println("Returning cached users...");
            return Mono.just(cachedUsers.get());
        } else {
            // If no cached data is found, return an empty list or a default response
            System.out.println("No cached data found, returning default empty list...");
            return Mono.just(List.of()); // Return an empty list or a default set of data
        }
    }
}


