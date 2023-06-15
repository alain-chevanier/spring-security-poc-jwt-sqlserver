package com.wizeline.spring.security.auth.test.utils;

import com.wizeline.spring.security.auth.models.Role;
import com.wizeline.spring.security.auth.models.User;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserStubber extends DataStubber<User, Long> {

  public UserStubber(JpaRepository<User, Long> repository) {
    super(repository);
  }

  @Override
  public User stub(Map<String, Object> defaultValues) {
    long currentMS = System.currentTimeMillis();
    String fourDigits = Long.toString(currentMS).substring(8);
    currentMS = Long.parseLong(fourDigits);
    User user = new User();
    user.setId((Long) defaultValues.getOrDefault("id", null));
    user.setUsername((String) defaultValues.getOrDefault("username", String.format("user-%d", currentMS)));
    user.setEmail((String) defaultValues.getOrDefault("email", String.format("user-%d@email.com", currentMS)));
    user.setPassword((String) defaultValues.getOrDefault("password", "user.password"));
    user.setRoles((Set<Role>) defaultValues.getOrDefault("roles", new HashSet<>()));
    return user;
  }
}
