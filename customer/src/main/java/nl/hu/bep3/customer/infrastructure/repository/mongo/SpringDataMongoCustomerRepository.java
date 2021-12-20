package nl.hu.bep3.customer.infrastructure.repository.mongo;

import nl.hu.bep3.customer.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoCustomerRepository extends MongoRepository<Customer, UUID> {


}
