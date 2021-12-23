package nl.hu.bep3.customer.domain.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepository {
  Optional<Customer> findByPhoneNumberOrEmail(String phoneNumber, String email);

  Optional<Customer> findById(UUID id);

  Page<Customer> findAllPaginated(Pageable pageable);

  Customer save(Customer customer);

  void delete(UUID id);
}
