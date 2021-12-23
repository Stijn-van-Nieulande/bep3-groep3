package nl.hu.bep3.management.infrastracture.repository.mongo;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoEmployeeRepository implements EmployeeRepository {

  private final SpringDataMongoEmployeeRepository repository;

  public MongoEmployeeRepository(final SpringDataMongoEmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Employee> findById(final UUID id) {
    return this.repository.findById(id);
  }

  @Override
  public Page<Employee> findAllPaginated(final Pageable pageable) {
    return this.repository.findAll(pageable);
  }

  @Override
  public Employee save(final Employee employee) {
    return this.repository.save(employee);
  }

  @Override
  public void deleteById(final UUID id) {
    this.repository.deleteById(id);
  }

  @Override
  public boolean doesExist(final String firstName, final String lastname) {
    return this.repository.existsByFirstNameAndLastName(firstName, lastname);
  }
}
