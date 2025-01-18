package com.example.candid.Integration_POC;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InMemoryUserCacheServiceImpl implements UserCacheService {

    private List<User> cachedUsers;  // In-memory cache

    @Override
    public Optional<List<User>> getCachedUsers() {
        return Optional.ofNullable(cachedUsers);  // Return cached users if available
    }

    @Override
    public void cacheUsers(List<User> users) {
        this.cachedUsers = users;  // Store the list of users in the cache
    }
}