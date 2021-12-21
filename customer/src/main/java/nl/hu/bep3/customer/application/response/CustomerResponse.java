package nl.hu.bep3.customer.application.response;

import nl.hu.bep3.customer.domain.Customer;
import java.util.UUID;

public class CustomerResponse {
  public Customer customer;

  public static CustomerResponse of(final Customer customer) {
    final CustomerResponse response = new CustomerResponse();
    response.customer = customer;
    return response;
  }
}
