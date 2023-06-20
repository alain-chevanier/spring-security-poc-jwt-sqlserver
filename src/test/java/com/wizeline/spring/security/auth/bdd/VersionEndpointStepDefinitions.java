package com.wizeline.spring.security.auth.bdd;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class VersionEndpointStepDefinitions extends SpringIntegrationTest {
  @When("^the client calls /version$")
  public void the_client_issues_GET_version() throws Throwable {
    executeGet("/version", Void.class);
  }

  @And("^the client receives server version (.+)$")
  public void the_client_receives_server_version_body(String version) throws Throwable {
    String response = lastResponseEntity.getBody().toString();
    assertThat(response, is(version));
  }
}