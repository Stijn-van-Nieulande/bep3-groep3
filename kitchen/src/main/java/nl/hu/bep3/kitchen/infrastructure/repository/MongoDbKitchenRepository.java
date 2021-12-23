package nl.hu.bep3.kitchen.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoDbKitchenRepository
    implements nl.hu.bep3.kitchen.domain.repository.KitchenRepository {

  private final SpringDataMongoKitchenRepository springDataMongoKitchenRepository;

  @Autowired
  public MongoDbKitchenRepository(
      final SpringDataMongoKitchenRepository springDataMongoKitchenRepository) {
    this.springDataMongoKitchenRepository = springDataMongoKitchenRepository;
  }

  @Override
  public Optional<Kitchen> findById(final UUID id) {
    return this.springDataMongoKitchenRepository.findById(id);
  }

  @Override
  public Kitchen save(final Kitchen kitchen) {
    return this.springDataMongoKitchenRepository.save(kitchen);
  }

  @Override
  public void delete(final Kitchen kitchen) {
    this.springDataMongoKitchenRepository.delete(kitchen);
  }

  @Override
  public Optional<Kitchen> findFirstByAddress(final String address) {
    return this.springDataMongoKitchenRepository.findFirstByAddress(address);
  }

  @Override
  public List<Kitchen> findAll() {
    return this.springDataMongoKitchenRepository.findAll();
  }
}
