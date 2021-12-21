package nl.hu.bep3.customer.domain.service;

import nl.hu.bep3.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Page<Customer> findAllPaginated(Pageable pageable);

    Optional<Customer> findCustomer(UUID id);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(UUID id);
}
