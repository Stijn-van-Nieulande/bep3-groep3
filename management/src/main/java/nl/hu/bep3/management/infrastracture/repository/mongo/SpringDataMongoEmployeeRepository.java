package nl.hu.bep3.management.infrastracture.repository.mongo;

import nl.hu.bep3.management.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoEmployeeRepository extends MongoRepository<Employee, UUID> {}
