package com.wizeline.spring.security.auth.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.spring.security.auth.payload.request.SignupRequest;
import com.wizeline.spring.security.auth.repository.UserRepository;
import com.wizeline.spring.security.auth.test.utils.UserStubber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wizeline.spring.security.auth.models.User;

@SpringBootTest
@Tag("IntegrationTest")
@AutoConfigureMockMvc
class UserRegistrationIntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  UserStubber userStubber;

  @BeforeEach
  void setUp() {
    userStubber = new UserStubber(userRepository);
  }

  @Test
  void registerHappyPath() throws Exception {
    User user = userStubber.stub();
    SignupRequest request = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();
    mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message", is("User registered successfully!")));
  }

  @Test
  void registerExistingUser() throws Exception {
    User user = userStubber.stubPersisted();
    SignupRequest request = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();
    mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().is4xxClientError());
  }

  @Test
  void registerBadRequest() throws Exception {
    SignupRequest request = SignupRequest.builder()
      .username("")
      .password("")
      .email("")
      .role(Set.of())
      .build();
    mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().isBadRequest());
  }
}

