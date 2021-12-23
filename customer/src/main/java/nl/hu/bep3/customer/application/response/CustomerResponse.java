package nl.hu.bep3.customer.application.response;

import nl.hu.bep3.customer.domain.Customer;

public class CustomerResponse {

  public Customer customer;

  public static CustomerResponse of(final Customer customer) {
    final CustomerResponse response = new CustomerResponse();
    response.customer = customer;
    return response;
  }
}
