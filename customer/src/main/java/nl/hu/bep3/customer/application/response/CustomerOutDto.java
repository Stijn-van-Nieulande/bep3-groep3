package nl.hu.bep3.customer.application.response;

import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;

public class CustomerOutDto {

  public UUID id;
  public String firstName;
  public String lastName;
  public String address;
  public String email;
  public String phoneNumber;

  public CustomerOutDto(final Customer customer) {
    this.id = customer.getId();
    this.firstName = customer.getFirstName();
    this.lastName = customer.getLastName();
    this.address = customer.getAddress();
    this.email = customer.getEmail();
    this.phoneNumber = customer.getPhoneNumber();
  }
}
