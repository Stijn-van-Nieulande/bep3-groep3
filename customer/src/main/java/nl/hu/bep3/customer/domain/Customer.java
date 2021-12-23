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
      final String firstName,
      final String lastName,
      final String address,
      final String email,
      final String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Customer(
      final UUID id,
      final String firstName,
      final String lastName,
      final String address,
      final String email,
      final String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public UUID getId() {
    return this.id;
  }

  @Override
  public void setId(final UUID id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return this.getId() == null;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
