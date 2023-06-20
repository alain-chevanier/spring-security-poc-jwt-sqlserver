package com.wizeline.spring.security.auth.bdd;

import io.cucumber.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenericStepDefinitions extends SpringIntegrationTest {
  @Then("^the client receives status code of (\\d+)$")
  public void the_client_receives_status_code_of(int statusCode) throws Throwable {
    assertThat(lastResponseEntity.getStatusCode().value(), is(statusCode));
  }
}
