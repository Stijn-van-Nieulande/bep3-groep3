package nl.hu.bep3.customer.infrastructure.repository.mongo;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoCustomerRepository extends MongoRepository<Customer, UUID> {
  public Optional<Customer> findCustomerByPhoneNumberOrEmail(String phoneNumber, String email);
}
