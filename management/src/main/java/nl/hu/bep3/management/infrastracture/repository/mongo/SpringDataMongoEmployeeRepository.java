package nl.hu.bep3.management.infrastracture.repository.mongo;

import java.util.UUID;
import nl.hu.bep3.management.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoEmployeeRepository extends MongoRepository<Employee, UUID> {}
