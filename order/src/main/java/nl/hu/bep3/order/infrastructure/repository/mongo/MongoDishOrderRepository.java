package nl.hu.bep3.order.infrastructure.repository.mongo;

import nl.hu.bep3.order.domain.repository.DishOrderRepository;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoDishOrderRepository implements DishOrderRepository {

  private final SpringDataMongoDishOrderRepository repository;

  public MongoDishOrderRepository(final SpringDataMongoDishOrderRepository repository) {
    this.repository = repository;
  }

  @Override
  public DishOrder save(final DishOrder dishOrder) {
    return this.repository.save(dishOrder);
  }
}
