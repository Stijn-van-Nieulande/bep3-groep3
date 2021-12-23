package nl.hu.bep3.management.domain.service;

import com.cookingfox.guava_preconditions.Preconditions;
import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.Role;
import nl.hu.bep3.management.domain.exceptions.EmployeeNotFoundException;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DomainEmployeeService implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  public DomainEmployeeService(final EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee createEmployee(final @NotNull Employee employee) {
    Preconditions.checkNotNull(employee, "The employee object cannot be empty.");

    return this.employeeRepository.save(employee);
  }

  @Override
  public void deleteEmployee(final @NotNull UUID id) {
    Preconditions.checkNotNull(id, "The employee id cannot be empty.");

    this.employeeRepository.deleteById(id);
  }

  @Override
  public Page<Employee> findAllPaginated(final @NotNull Pageable pageable) {
    Preconditions.checkNotNull(
        pageable, "The pageable object was null but is required to allow pagination.");

    return this.employeeRepository.findAllPaginated(pageable);
  }

  @Override
  public Employee changeEmployeeName(
      final @NotNull UUID id, final @NotNull String firstName, final @NotNull String lastName) {
    Preconditions.checkNotNull(id, "The employee id cannot be empty.");

    final Employee employee =
        this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    employee.setFirstName(firstName);
    employee.setLastName(lastName);

    return this.employeeRepository.save(employee);
  }

  @Override
  public Employee changeEmployeeRole(@NotNull final UUID id, @NotNull final Role role) {
    Preconditions.checkNotNull(id, "The employee id cannot be empty.");

    final Employee employee =
        this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    employee.setRole(role);

    return this.employeeRepository.save(employee);
  }

  @Override
  public Employee changeEmployeeSalaris(@NotNull final UUID id, final double salaris) {
    Preconditions.checkNotNull(id, "The employee id cannot be empty.");

    final Employee employee =
        this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    employee.setSalaris(salaris);

    return this.employeeRepository.save(employee);
  }

  @Override
  public boolean doesExist(final String firstName, final String lastname) {
    return this.employeeRepository.doesExist(firstName, lastname);
  }
}
