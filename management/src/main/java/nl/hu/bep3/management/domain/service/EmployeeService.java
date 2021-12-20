package nl.hu.bep3.management.domain.service;

import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
  Employee createEmployee(Employee employee);

  void deleteEmployee(UUID id);

  Page<Employee> findAllPaginated(Pageable pageable);
}
