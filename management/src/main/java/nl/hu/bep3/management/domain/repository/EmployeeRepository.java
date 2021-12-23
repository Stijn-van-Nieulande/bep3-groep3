package nl.hu.bep3.management.domain.repository;

import nl.hu.bep3.management.domain.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {
  Optional<Employee> findById(UUID id);

  UUID save(Employee employee);
}
