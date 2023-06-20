package com.wizeline.spring.security.auth.bdd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.spring.security.auth.SpringBootSecuritySQLServerApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootSecuritySQLServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Slf4j
public class SpringIntegrationTest {
  static RequestEntity<?> lastRequestEntity;
  static ResponseEntity<?> lastResponseEntity;

  @Autowired
  protected RestTemplate restTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Value("${testing-server.baseurl}")
  private String baseUrl;

  <S> void executeGet(String url, Class<S> returnType) throws URISyntaxException {
    HttpHeaders headers =  new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(baseUrl + url));
    ResponseEntity<S> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {});
    lastRequestEntity = requestEntity;
    lastResponseEntity = responseEntity;
  }

  <T, S> void executePost(String url, T requestBody, Class<S> returnType) throws URISyntaxException {
    HttpHeaders headers =  new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RequestEntity<T> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, new URI(baseUrl + url));
    try {
      ResponseEntity<S> responseEntity = restTemplate.exchange(requestEntity, returnType);
      lastResponseEntity = responseEntity;
      lastRequestEntity = requestEntity;
    } catch (HttpClientErrorException e) {
      lastResponseEntity = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }
}
