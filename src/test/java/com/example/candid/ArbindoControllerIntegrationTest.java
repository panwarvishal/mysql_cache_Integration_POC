package com.example.candid;

import com.example.candid.Integration_POC.Arbindo_controller;
import com.example.candid.Integration_POC.User;
import com.example.candid.Integration_POC.UserCacheService;
import com.example.candid.Integration_POC.UserClientImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CandidApplication.class)

@Import({TestConfig.class}) // Import test configuration for mocking beans if needed
class ArbindoControllerIntegrationTest {

    @Autowired
    private Arbindo_controller arbindoController;

    @Autowired
    private WebTestClient webTestClient;

//    @Autowired
//    private MockWebServer mockWebServer;

    @Autowired
    private UserCacheService userCacheService;

//    @BeforeEach
//    void setUp() throws IOException {
//        mockWebServer.start();
//    }
//
//    @AfterEach
//    void tearDown() throws IOException {
//        mockWebServer.shutdown();
//    }


    @Autowired
    UserClientImpl userClientImpl;

    @Test
    void testGetUsers_whenApiCallSucceeds() throws Exception {
        // Mock the external API response
//        String userJson = "[{\"id\":1,\"name\":\"John Doe\"},{\"id\":2,\"name\":\"Jane Doe\"}]";
//        mockWebServer.enqueue(new MockResponse()
//                .setResponseCode(HttpStatus.OK.value())
//                .setBody(userJson)
//                .addHeader("Content-Type", "application/json"));

        Mono<List<User>> mockResponse = Mono.just(
                Arrays.asList(new User("12", "Vishal", "alice@example.com"),
                        new User("23", "Monu", "bob@example.com"))
        );
       // when(userClientImpl.fetchUsers(any())).thenReturn(mockResponse);
        when(userClientImpl.fetchUsers(anyString())).thenReturn(mockResponse);

        // Perform the test
        webTestClient.get()
                .uri("/user/v1/")
                .exchange()
                //.doOnRequest(request -> log.info("Request made to /user/v1/"))
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .consumeWith(response -> {
                    List<User> users = response.getResponseBody();
                    System.out.println("Response body: =============" + users);
                    assertThat(users).isNotNull();
                    assertThat(users).hasSize(2);
                    assertThat(users.get(0).getName()).isEqualTo("Vishal");
                });

    }

}
