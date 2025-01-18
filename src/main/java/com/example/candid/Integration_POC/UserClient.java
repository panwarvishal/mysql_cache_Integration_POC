package com.example.candid.Integration_POC;

import reactor.core.publisher.Mono;

import java.util.List;

public interface UserClient {
    Mono<List<User>> fetchUsers(String apiKey);

}
