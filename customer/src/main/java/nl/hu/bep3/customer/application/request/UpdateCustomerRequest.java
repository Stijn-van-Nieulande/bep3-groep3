package nl.hu.bep3.customer.application.request;

import java.util.UUID;

public class UpdateCustomerRequest {
  public UUID id;
  public String firstName;
  public String lastName;
  public String address;
  public String email;
  public String phoneNumber;
}
