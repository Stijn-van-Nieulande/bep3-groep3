package nl.hu.bep3.customer.infrastructure.repository.mongo;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoDbCustomerRepository implements CustomerRepository {

  private final SpringDataMongoCustomerRepository customerRepository;

  @Autowired
  public MongoDbCustomerRepository(final SpringDataMongoCustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Optional<Customer> findByPhoneNumberOrEmail(final String phoneNumber, final String email) {
    return Optional.empty();
  }

  @Override
  public Optional<Customer> findById(final UUID id) {
    return this.customerRepository.findById(id);
  }

  @Override
  public Page<Customer> findAllPaginated(final Pageable pageable) {
    return this.customerRepository.findAll(pageable);
  }

  @Override
  public Customer save(final Customer customer) {
    return this.customerRepository.save(customer);
  }

  @Override
  public void delete(final UUID id) {
    this.customerRepository.deleteById(id);
  }
}
