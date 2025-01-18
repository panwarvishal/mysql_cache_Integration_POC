package com.example.candid.Integration_POC;

import java.util.List;
import java.util.Optional;

public interface UserCacheService {
    Optional<List<User>> getCachedUsers();   // To fetch cached data if available
    void cacheUsers(List<User> users);       // To store data in the cache
}