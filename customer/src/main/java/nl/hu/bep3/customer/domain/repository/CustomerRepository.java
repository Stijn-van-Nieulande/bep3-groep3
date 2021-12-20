package nl.hu.bep3.customer.domain.repository;

import nl.hu.bep3.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
  Optional<Customer> findById(UUID id);

  Page<Customer> findAllPaginated(Pageable pageable);

  Customer save(Customer customer);

  void delete(UUID id);
}
