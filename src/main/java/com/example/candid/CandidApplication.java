package com.example.candid;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CandidApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidApplication.class, args);
	}

}
