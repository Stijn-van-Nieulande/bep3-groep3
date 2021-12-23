package nl.hu.bep3.customer.infrastructure.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Persistable<T> extends org.springframework.data.domain.Persistable<T> {

  void setId(T id);

  @Override
  @JsonIgnore
  boolean isNew();
}
