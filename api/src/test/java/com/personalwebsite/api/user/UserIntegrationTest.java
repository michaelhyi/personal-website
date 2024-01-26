package com.personalwebsite.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-it.properties")
class UserIntegrationTest {
  @Autowired
  private WebTestClient client;

  @Autowired
  private UserRepository repository;

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void readUserByEmail() {
    client
      .get()
      .uri("/api/v1/user/null")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isBadRequest();

    client
      .get()
      .uri("/api/v1/user/undefined")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isBadRequest();

    client
      .get()
      .uri("/api/v1/user/test@mail.com")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isNotFound();
    
    User expected = new User("test@mail.com");
    repository.save(expected);

    assertEquals(expected.getId(), client
      .get()
      .uri("/api/v1/user/test@mail.com")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody(new ParameterizedTypeReference<User>() {})
      .returnResult()
      .getResponseBody()
      .getId());
  }
}
