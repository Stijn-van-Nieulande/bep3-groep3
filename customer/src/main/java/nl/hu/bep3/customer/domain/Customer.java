package nl.hu.bep3.customer.domain;

import java.util.UUID;
import nl.hu.bep3.customer.infrastructure.repository.Persistable;

public class Customer implements Persistable<UUID> {
  private UUID id;
  private String firstName;
  private String lastName;
  private String address;
  private String email;
  private String phoneNumber;

  public Customer() {}

  public Customer(
      String firstName, String lastName, String address, String email, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Customer(
      UUID id,
      String firstName,
      String lastName,
      String address,
      String email,
      String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public UUID getId() {
    return id;
  }

  @Override
  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return this.getId() == null;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
