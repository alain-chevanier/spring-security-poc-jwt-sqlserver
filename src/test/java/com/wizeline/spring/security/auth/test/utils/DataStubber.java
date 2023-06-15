package com.wizeline.spring.security.auth.test.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

@AllArgsConstructor
public abstract class DataStubber<T, K> {

  protected JpaRepository<T,K> repository;

  public T stub() {
    return this.stub(Map.of());
  }

  public abstract T stub(Map<String, Object> defaultValues);

  public T stubPersisted() {
    return this.stubPersisted(Map.of());
  }

  public T stubPersisted(Map<String, Object> defaultValues) {
    T instance = this.stub(defaultValues);
    return repository.save(instance);
  }

}
