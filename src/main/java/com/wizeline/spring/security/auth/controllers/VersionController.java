package com.wizeline.spring.security.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

  @GetMapping("/version")
  public String getVersion() {
    return "1.0";
  }
}
