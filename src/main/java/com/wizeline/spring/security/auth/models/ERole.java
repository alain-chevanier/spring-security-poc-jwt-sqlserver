package com.wizeline.spring.security.auth.models;

public enum ERole {
  ROLE_USER("ROLE_USER"),
  ROLE_MODERATOR("ROLE_MODERATOR"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String value;

  ERole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
