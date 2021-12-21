package nl.hu.bep3.customer.infrastructure.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Persistable<ID> extends org.springframework.data.domain.Persistable<ID> {
    void setId(ID id);

    @Override
    @JsonIgnore
    boolean isNew();
}
