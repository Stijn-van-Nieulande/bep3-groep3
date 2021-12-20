package nl.hu.bep3.management.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hu.bep3.management.infrastracture.repository.Persistable;

import java.util.Objects;
import java.util.UUID;

public class Employee implements Persistable<UUID>
{
  private UUID id;
  private String firstName;
  private String lastName;
  private Double salaris;

  @JsonCreator
  public Employee(
      @JsonProperty("firstName") final String firstName,
      @JsonProperty("lastName") final String lastName,
      @JsonProperty("salaris") final Double salaris) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salaris = salaris;
  }

  @JsonCreator
  public Employee(
      @JsonProperty("id") final UUID id,
      @JsonProperty("firstName") final String firstName,
      @JsonProperty("lastName") final String lastName,
      @JsonProperty("salaris") final Double salaris) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salaris = salaris;
  }

  public UUID getId() {
    return this.id;
  }

  @Override
  public void setId(final UUID id)
  {
    this.id = id;
  }

  @Override
  public boolean isNew()
  {
    return this.getId() == null;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName)
  {
    this.lastName = lastName;
  }

  public Double getSalaris() {
    return this.salaris;
  }

  public void setSalaris(final Double salaris)
  {
    this.salaris = salaris;
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) return true;
    if (!(o instanceof final Employee employee)) return false;
    if(this.getId() == null) return false;
    return this.getId().equals(employee.getId()) && Objects.equals(this.getFirstName(), employee.getFirstName()) && Objects.equals(this.getLastName(), employee.getLastName()) && Objects.equals(this.getSalaris(), employee.getSalaris());
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.getId(), this.getFirstName(), this.getLastName(), this.getSalaris());
  }
}
