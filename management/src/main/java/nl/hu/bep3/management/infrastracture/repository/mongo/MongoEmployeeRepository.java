package nl.hu.bep3.management.infrastracture.repository.mongo;

import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoEmployeeRepository implements EmployeeRepository {
  private final SpringDataMongoEmployeeRepository repository;

  public MongoEmployeeRepository(final SpringDataMongoEmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Employee> findById(UUID id) {
    return Optional.empty();
  }

  @Override
  public UUID save(Employee employee) {
    return this.repository.save(employee).getId();
  }
}
