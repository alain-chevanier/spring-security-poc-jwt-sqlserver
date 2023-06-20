package com.wizeline.spring.security.auth.bdd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.wizeline.spring.security.auth.SpringBootSecuritySQLServerApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootSecuritySQLServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")
@Slf4j
public class SpringIntegrationTest {
  static RequestEntity<?> lastRequestEntity;
  static ResponseEntity<?> lastResponseEntity;

  @Autowired
  protected RestTemplate restTemplate;

  @Value("${testing-server.baseurl}")
  private String baseUrl;

  <R> void executeGet(String url, Class<R> returnType) throws URISyntaxException {
    HttpHeaders headers =  new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(baseUrl + url));
    lastRequestEntity = requestEntity;
    try {
      lastResponseEntity = restTemplate.exchange(requestEntity, returnType);
    } catch (HttpClientErrorException e) {
      lastResponseEntity = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  <T, S> void executePost(String url, T requestBody, Class<S> returnType) throws URISyntaxException {
    HttpHeaders headers =  new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RequestEntity<T> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, new URI(baseUrl + url));
    lastRequestEntity = requestEntity;
    try {
      lastResponseEntity = restTemplate.exchange(requestEntity, returnType);
    } catch (HttpClientErrorException e) {
      lastResponseEntity = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }
}
