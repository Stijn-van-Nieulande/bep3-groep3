package nl.hu.bep3.management.domain.service;

import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DomainEmployeeService implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  public DomainEmployeeService(final EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee createEmployee(final Employee employee) {
    return this.employeeRepository.save(employee);
  }

  @Override
  public void deleteEmployee(final UUID id) {
    this.employeeRepository.deleteById(id);
  }

  @Override
  public Page<Employee> findAllPaginated(final Pageable pageable) {
    return this.employeeRepository.findAllPaginated(pageable);
  }
}
