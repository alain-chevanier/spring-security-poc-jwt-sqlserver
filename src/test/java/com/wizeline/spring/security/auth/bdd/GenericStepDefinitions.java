package com.wizeline.spring.security.auth.bdd;

import io.cucumber.java.en.Then;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenericStepDefinitions extends SpringIntegrationTest {
  @Then("^the client receives status code of (\\d+)$")
  public void theRequestReturnAStatusCodeOf(int statusCode) throws Throwable {
    assertThat(lastResponseEntity.getStatusCode().value(), is(statusCode));
  }

  @Then("^the request succeeds$")
  public void theRequestReturnIsSuccessful() {
    assertThat(lastResponseEntity.getStatusCode(), is(HttpStatus.OK));
  }

  @Then("^the request fails with conflict$")
  public void theRequestFailsWithConflict() {
    assertThat(lastResponseEntity.getStatusCode(), is(HttpStatus.CONFLICT));
  }

  @Then("^the request fails with bad request$")
  public void theRequestFailsWithBadRequest() {
    assertThat(lastResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
  }
}

