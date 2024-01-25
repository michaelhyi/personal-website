package com.personalwebsite.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class UserIntegrationTest {
  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private UserRepository repository;

  @Autowired
  private MockMvc mockMvc;

  @AfterEach
  void tearDown() {
  }

  @Test
  void readUserByEmail() throws Exception {
    mockMvc
      .perform(get("/api/v1/user/null").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());

    mockMvc
      .perform(get("/api/v1/user/undefined").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());

    mockMvc
      .perform(get("/api/v1/user/test@mail.com").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

    User expected = new User("test@mail.com");
    repository.save(expected);

    String result = mockMvc
      .perform(get("/api/v1/user/test@mail.com").contentType(MediaType.APPLICATION_JSON)) 
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    User actual = mapper.readValue(result, new TypeReference<User>() {}); 
    assertEquals(expected, actual);
  } 
  
}
