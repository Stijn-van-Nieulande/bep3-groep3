package nl.hu.bep3.management.domain.exceptions;

/** Exception thrown when an employee is not found. */
public class EmployeeAlreadyExistsException extends RuntimeException {

  public EmployeeAlreadyExistsException(final String message) {
    super(message);
  }

  public EmployeeAlreadyExistsException(final String firstName, final String lastName) {
    super(
        String.format(
            "The employee with first name \"%s\" and last name \"%s\" already exists.",
            firstName, lastName));
  }
}
