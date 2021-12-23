package nl.hu.bep3.management.domain.exceptions;

import java.util.UUID;

/** Exception thrown when an employee is not found. */
public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(final String message) {
    super(message);
  }

  public EmployeeNotFoundException(final UUID id) {
    super(String.format("The employee with id \"%s\" could not be found.", id));
  }
}
