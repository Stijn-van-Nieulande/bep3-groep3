package nl.hu.bep3.customer.infrastructure.repository;

public interface Persistable<ID> extends org.springframework.data.domain.Persistable<ID> {
    void setId(ID id);
}
