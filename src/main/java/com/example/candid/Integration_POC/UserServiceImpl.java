package com.example.candid.Integration_POC;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public Mono<List<User>> getUsers(String apiKey) {
        return userClient.fetchUsers(apiKey);
    }
}