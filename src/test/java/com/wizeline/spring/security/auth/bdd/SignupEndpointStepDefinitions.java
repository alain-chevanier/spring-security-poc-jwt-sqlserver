package com.wizeline.spring.security.auth.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.spring.security.auth.models.User;
import com.wizeline.spring.security.auth.payload.request.SignupRequest;
import com.wizeline.spring.security.auth.repository.UserRepository;
import com.wizeline.spring.security.auth.test.utils.UserStubber;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class SignupEndpointStepDefinitions extends SpringIntegrationTest {

  public static User lastRegisteredUser;
  public static SignupRequest lastSignupRequest;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  UserStubber userStubber;

  @Before
  public void before() {
    userStubber = new UserStubber(userRepository);
  }

  @Given("^a valid signup request$")
  public void createAValidSignupRequest() {
    User user = userStubber.stub();
    lastRegisteredUser = user;
    lastSignupRequest = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();
  }

  @Given("^a signup request with a username from an existing user$")
  public void createAnExistingUsernameSignupRequest() {
    User user = userStubber.stubPersisted();
    lastRegisteredUser = user;
    lastSignupRequest = SignupRequest.builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .email(user.getEmail())
      .role(Set.of("ROLE_USER"))
      .build();
  }

  @Given("^a signup request with empty values$")
  public void createEmptySignupRequest() {
    lastRegisteredUser = null;
    lastSignupRequest = SignupRequest.builder()
      .username("")
      .password("")
      .email("")
      .role(Set.of())
      .build();
  }

  @When("^a request is made to /signup$")
  public void makeARequestToSignup() throws Throwable {
    executePost("/api/auth/signup", lastSignupRequest, String.class);
  }

  @And("^the user is registered$")
  public void theClientReceivesAMessageOfSuccess() throws Throwable {
    String response = lastResponseEntity.getBody().toString();
    assertThat(response, is("{\"message\":\"User registered successfully!\"}"));
  }

  @And("^a \"user already exists\" message is returned$")
  public void theClientReceivesAUserAlreadyExistsMessage() throws Throwable {
    String response = lastResponseEntity.getBody().toString();
    assertThat(response, is("{\"message\":\"Error: Username is already taken!\"}"));
  }
}
