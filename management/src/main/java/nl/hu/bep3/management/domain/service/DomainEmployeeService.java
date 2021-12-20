package nl.hu.bep3.management.domain.service;

import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;

import java.util.UUID;

public class DomainEmployeeService implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  public DomainEmployeeService(final EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public UUID createEmployee(final Employee employee) {
    return this.employeeRepository.save(employee);
  }
}
