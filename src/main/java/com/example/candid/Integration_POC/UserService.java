package com.example.candid.Integration_POC;

import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {
    Mono<List<User>> getUsers(String apiKey);

}
