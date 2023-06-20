package com.wizeline.spring.security.auth.bdd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wizeline.spring.security.auth.SpringBootSecuritySQLServerApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootSecuritySQLServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Slf4j
public class SpringIntegrationTest {
  static RequestEntity<?> lastRequestEntity;
  static ResponseEntity<?> lastResponseEntity;

  @Autowired
  protected RestTemplate restTemplate;

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
    ResponseEntity<S> responseEntity = restTemplate.exchange(requestEntity, returnType);
    lastRequestEntity = requestEntity;
    lastResponseEntity = responseEntity;
  }
}
