package com.wizeline.spring.security.auth.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.spring.security.auth.models.User;
import com.wizeline.spring.security.auth.payload.request.SignupRequest;
import com.wizeline.spring.security.auth.repository.UserRepository;
import com.wizeline.spring.security.auth.test.utils.UserStubber;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class SignupEndpointStepDefinitions extends SpringIntegrationTest {

  public static User lastRegisteredUser;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  UserStubber userStubber;

  @When("^the client calls /signup with valid information$")
  public void the_client_issues_GET_version() throws Throwable {
    userStubber = new UserStubber(userRepository);
    User user = userStubber.stub();
    SignupRequest request = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();
    executePost("/api/auth/signup", request, String.class);
    lastRegisteredUser = user;
  }

  @And("^the user was registered successfully$")
  public void the_client_receives_server_version_body() throws Throwable {
    String response = lastResponseEntity.getBody().toString();
    assertThat(response, is("{\"message\":\"User registered successfully!\"}"));
  }
}
