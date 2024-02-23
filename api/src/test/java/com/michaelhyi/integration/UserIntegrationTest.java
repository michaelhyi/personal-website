package com.michaelhyi.integration;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.entity.User;

@SpringBootTest
@TestPropertySource("classpath:application-it.properties")
@AutoConfigureMockMvc
class UserIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private UserRepository repository;

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  // @Test
  void readUserByEmail() throws Exception {
    mockMvc.perform(get("/v1/user/null")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest()); 

    mockMvc.perform(get("/v1/user/undefined")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest()); 

    mockMvc.perform(get("/v1/user/test@mail.com")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
    
    User expected = User.builder()
                        .email("test@mail.com")
                        .build();
    repository.save(expected);

    String response = mockMvc
                          .perform(get("/v1/user/test@mail.com")
                          .accept(MediaType.APPLICATION_JSON))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();
    
    User actual = mapper.readValue(response, User.class);

    assertEquals(expected.getId(), actual.getId());
  }
}
