package nl.hu.bep3.customer.domain.service;

import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class DomainCustomerService implements CustomerService{
    private final CustomerRepository customerRepository;

    public DomainCustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
      if(this.customerRepository.findByPhoneNumberOrEmail(customer.getPhoneNumber(), customer.getEmail()).isEmpty()){
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
        return null;
    }

    @Override
    public void deleteCustomer(UUID id) {

    }
}
