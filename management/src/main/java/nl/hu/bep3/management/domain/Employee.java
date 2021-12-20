package nl.hu.bep3.management.domain;

import java.util.Objects;
import java.util.UUID;
import nl.hu.bep3.management.infrastracture.repository.Persistable;

public class Employee implements Persistable<UUID> {
  private UUID id;
  private String firstName;
  private String lastName;
  private Double salaris;
  private Role role;

  public Employee(
      final String firstName,
      final String lastName,
      final Double salaris,
      final Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salaris = salaris;
    this.role = role;
  }

  public Employee(
      final UUID id,
      final String firstName,
      final String lastName,
      final Double salaris,
      final Role role) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salaris = salaris;
    this.role = role;
  }

  public Employee() {
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

  public Double getSalaris() {
    return this.salaris;
  }

  public void setSalaris(final Double salaris) {
    this.salaris = salaris;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(final Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final Employee employee)) {
      return false;
    }
    if (this.getId() == null) {
      return false;
    }
    return this.getId().equals(employee.getId())
        && Objects.equals(this.getFirstName(), employee.getFirstName())
        && Objects.equals(this.getLastName(), employee.getLastName())
        && Objects.equals(this.getSalaris(), employee.getSalaris());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId(), this.getFirstName(), this.getLastName(), this.getSalaris());
  }
}
