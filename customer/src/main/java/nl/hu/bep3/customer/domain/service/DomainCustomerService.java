package nl.hu.bep3.customer.domain.service;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.exceptions.CustomerNotFoundException;
import nl.hu.bep3.customer.domain.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DomainCustomerService implements CustomerService {
  private final CustomerRepository customerRepository;

  public DomainCustomerService(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer createCustomer(Customer customer) {
    if (this.customerRepository
        .findByPhoneNumberOrEmail(customer.getPhoneNumber(), customer.getEmail())
        .isEmpty()) {
      return this.customerRepository.save(customer);
    }
    return null;
  }

  @Override
  public Page<Customer> findAllPaginated(Pageable pageable) {
    return this.customerRepository.findAllPaginated(pageable);
  }

  @Override
  public Optional<Customer> findCustomer(UUID id) {
    return this.customerRepository.findById(id);
  }

  @Override
  public Customer updateCustomer(Customer customer) {
    Optional<Customer> optionalCustomer = this.customerRepository.findById(customer.getId());
    if (optionalCustomer.isPresent()) {
      return this.customerRepository.save(customer);
    } else {
      throw new CustomerNotFoundException(customer.getId());
    }
  }

  @Override
  public void deleteCustomer(UUID id) {
    this.customerRepository.delete(id);
  }
}
