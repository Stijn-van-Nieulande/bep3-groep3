package nl.hu.bep3.management.domain.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository {

  Optional<Employee> findById(UUID id);

  Page<Employee> findAllPaginated(Pageable pageable);

  Employee save(Employee employee);

  void deleteById(UUID uuid);

  boolean doesExist(String firstName, String lastname);
}
