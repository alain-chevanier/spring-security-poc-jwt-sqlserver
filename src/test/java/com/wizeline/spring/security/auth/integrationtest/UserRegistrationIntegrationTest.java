package com.wizeline.spring.security.auth.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.spring.security.auth.payload.request.SignupRequest;
import com.wizeline.spring.security.auth.repository.UserRepository;
import com.wizeline.spring.security.auth.test.utils.UserStubber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wizeline.spring.security.auth.models.User;
import org.springframework.test.web.servlet.ResultActions;

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
  void registerUserSuccessfully() throws Exception {
    // 1.- prepare the data for the test
    User user = userStubber.stub();
    SignupRequest request = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();

    // 2.- perform the action/call
    ResultActions resultActions = mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      );

    // 3.- perform verifications/assertions
    resultActions.andExpect(status().isOk());
    Optional<User> createdUser = userRepository.findByUsername(user.getUsername());
    assertThat(createdUser).isNotEmpty();
  }

  @Test
  void registerExistingUser() throws Exception {
    // 1.- prepare the data for the test
    User user = userStubber.stubPersisted();
    SignupRequest request = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();

    // 2.- perform the action/call
    ResultActions resultActions = mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      );

    // 3.- perform verifications/assertions
    resultActions.andExpect(status().is4xxClientError());
  }

  @Test
  void registerBadRequest() throws Exception {
    SignupRequest request = SignupRequest.builder()
      .username("")
      .password("")
      .email("")
      .role(Set.of())
      .build();
    ResultActions resultActions = mockMvc.perform(
        post("/api/auth/signup")
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      );

    resultActions.andExpect(status().isBadRequest());
  }
}

