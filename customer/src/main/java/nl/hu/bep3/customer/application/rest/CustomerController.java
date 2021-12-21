package nl.hu.bep3.customer.application.rest;

import nl.hu.bep3.customer.application.request.CreateCustomerRequest;
import nl.hu.bep3.customer.application.response.CustomerResponse;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public CustomerResponse createCustomer(@RequestBody final CreateCustomerRequest createCustomerRequest) {
    final Customer customer = this.customerService.createCustomer(createCustomerRequest.customer);
    return CustomerResponse.of(customer);
  }

  @GetMapping()
  public Page<Customer> getCustomers(final Pageable pageable) {
    return this.customerService.findAllPaginated(pageable);
  }


}
