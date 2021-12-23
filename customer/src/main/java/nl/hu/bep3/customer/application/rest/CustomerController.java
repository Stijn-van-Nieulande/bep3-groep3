package nl.hu.bep3.customer.application.rest;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.application.request.CreateCustomerRequest;
import nl.hu.bep3.customer.application.request.UpdateCustomerRequest;
import nl.hu.bep3.customer.application.response.CustomerResponse;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.exceptions.CustomerNotFoundException;
import nl.hu.bep3.customer.domain.service.CustomerService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(final CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomerResponse createCustomer(
      @RequestBody final CreateCustomerRequest createCustomerRequest) {
    final Customer customer =
        this.customerService.createCustomer(
            new Customer(
                createCustomerRequest.firstName,
                createCustomerRequest.lastName,
                createCustomerRequest.address,
                createCustomerRequest.email,
                createCustomerRequest.phoneNumber));
    return CustomerResponse.of(customer);
  }

  @GetMapping()
  public Page<Customer> getCustomers(@ParameterObject final Pageable pageable) {
    return this.customerService.findAllPaginated(pageable);
  }

  @GetMapping(value = "/{customerId}")
  public CustomerResponse getCustomer(@PathVariable final UUID customerId) {
    final Optional<Customer> customer = this.customerService.findCustomer(customerId);

    if (customer.isPresent()) {
      return CustomerResponse.of(customer.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping()
  public CustomerResponse updateCustomer(
      @RequestBody final UpdateCustomerRequest updateCustomerRequest) {
    final Customer customer =
        new Customer(
            updateCustomerRequest.id,
            updateCustomerRequest.firstName,
            updateCustomerRequest.lastName,
            updateCustomerRequest.address,
            updateCustomerRequest.email,
            updateCustomerRequest.phoneNumber);
    try {
      return CustomerResponse.of(this.customerService.updateCustomer(customer));
    } catch (final CustomerNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "/{customerId}")
  public HttpStatus deleteCustomer(@PathVariable final UUID customerId) {
    this.customerService.deleteCustomer(customerId);
    return HttpStatus.OK;
  }
}
