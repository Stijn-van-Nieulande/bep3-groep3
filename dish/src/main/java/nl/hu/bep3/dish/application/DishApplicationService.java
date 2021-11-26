package nl.hu.bep3.dish.application;

import nl.hu.bep3.dish.adapter.DishRepository;
import nl.hu.bep3.dish.adapter.dto.DishOutDto;
import nl.hu.bep3.dish.application.Exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.Dish;
import org.springframework.stereotype.Service;

@Service
public class DishApplicationService {
  private DishRepository dishRepository;

  public DishApplicationService(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  public DishOutDto getDishById(Long id) {
    Dish dish =
        dishRepository
            .findById(id)
            .orElseThrow(() -> new DishNotFoundException("No dish found with id: " + id));
    return new DishOutDto(dish);
  }
}
