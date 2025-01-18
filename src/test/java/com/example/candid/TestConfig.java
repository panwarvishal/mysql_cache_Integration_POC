package com.example.candid;


import okhttp3.mockwebserver.MockWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public MockWebServer mockWebServer() {
        return new MockWebServer();
    }
}
