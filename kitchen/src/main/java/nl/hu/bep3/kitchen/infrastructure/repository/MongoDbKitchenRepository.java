package nl.hu.bep3.kitchen.infrastructure.repository;

import nl.hu.bep3.kitchen.domain.Kitchen;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class MongoDbKitchenRepository implements nl.hu.bep3.kitchen.domain.repository.KitchenRepository {

    private final SpringDataMongoKitchenRepository springDataMongoKitchenRepository;

    @Autowired
    public MongoDbKitchenRepository(final SpringDataMongoKitchenRepository springDataMongoKitchenRepository) {
        this.springDataMongoKitchenRepository = springDataMongoKitchenRepository;
    }

    @Override
    public Optional<Kitchen> findById(final ObjectId id) {
        return springDataMongoKitchenRepository.findById(id);
    }

    @Override
    public Kitchen save(final Kitchen kitchen) {
        return springDataMongoKitchenRepository.save(kitchen);
    }

    @Override
    public Optional<Kitchen> findFirstByAddress(String address) {
        return springDataMongoKitchenRepository.findFirstByAddress(address);
    }
}
