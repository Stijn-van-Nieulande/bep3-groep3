package nl.hu.bep3.dish.infrastructure.repository;

import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoDbDishRepository implements DishRepository {
    private final SpringDataMongoDishRepository springDataMongoDishRepository;

    @Autowired
    public MongoDbDishRepository(final SpringDataMongoDishRepository springDataMongoKitchenRepository) {
        this.springDataMongoDishRepository = springDataMongoKitchenRepository;
    }

    @Override
    public Optional<Dish> findById(final UUID id) {
        return springDataMongoDishRepository.findById(id);
    }

    @Override
    public Dish save(final Dish dish) {
        return springDataMongoDishRepository.save(dish);
    }

}
