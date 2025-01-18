package com.example.candid.Integration_POC;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user/v1")
@Slf4j
@Validated
@RequiredArgsConstructor
public class Arbindo_controller {

    private final UserService userServiceAdapter;
    @GetMapping("/")
    public ResponseEntity<Mono<List<User>>> getusers()
    {
      log.info("Service .. Aurbindo controller .........");
      Mono<List<User>> res= userServiceAdapter.getUsers("test");
      return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
