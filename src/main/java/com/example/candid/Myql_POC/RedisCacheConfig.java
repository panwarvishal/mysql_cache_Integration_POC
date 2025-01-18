package com.example.candid.Myql_POC;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    // Spring Boot auto-configures RedisCacheManager,
    // so no additional configuration is needed for most cases.
}