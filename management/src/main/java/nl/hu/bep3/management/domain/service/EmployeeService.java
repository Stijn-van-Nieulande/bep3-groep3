package nl.hu.bep3.management.domain.service;

import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
  /**
   * Create a new employee.
   *
   * @param employee The employee data.
   * @return The newly created {@link Employee} object.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   */
  Employee createEmployee(@NotNull Employee employee);

  /**
   * Delete the employee by the provided id.
   *
   * @param id The unique id of the employee to delete.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   */
  void deleteEmployee(@NotNull UUID id);

  /**
   * Find all employees (Paginated).
   *
   * @param pageable The {@see Pageable} object used for pagination.
   * @return The {@link Page} object containing all {@link Employee}s.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   */
  Page<Employee> findAllPaginated(@NotNull Pageable pageable);

  /**
   * Change the employee first and last name.
   *
   * @param id The employee id of the employee to change.
   * @param firstName The new employee first name.
   * @param lastName The new employee last name.
   * @return The updated {@link Employee}.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   * @throws IllegalArgumentException when some arguments do not meet the requirements.
   */
  Employee changeEmployeeName(
      @NotNull UUID id, @NotNull String firstName, @NotNull String lastName);

  /**
   * Change the employee role.
   *
   * @param id The employee id of the employee to change.
   * @param role The new employee role.
   * @return The updated {@link Employee}.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   * @throws IllegalArgumentException when some arguments do not meet the requirements.
   */
  Employee changeEmployeeRole(@NotNull UUID id, @NotNull Role role);

  /**
   * Change the employee salaris.
   *
   * @param id The employee id of the employee to change.
   * @param salaris The new employee salaris.
   * @return The updated {@link Employee}.
   * @throws NullPointerException when some arguments require a non-null value and a null value is
   *     provided.
   * @throws IllegalArgumentException when some arguments do not meet the requirements.
   */
  Employee changeEmployeeSalaris(@NotNull UUID id, double salaris);

  /**
   * Check if their already exists an employee with the provided name.
   *
   * @param firstName The first name of the employee.
   * @param lastname The last name of the employee.
   * @return True if the employee exists.
   */
  boolean doesExist(String firstName, String lastname);
}
