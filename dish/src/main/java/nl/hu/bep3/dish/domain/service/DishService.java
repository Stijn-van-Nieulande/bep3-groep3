package nl.hu.bep3.dish.domain.service;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;

public interface DishService {
    DishOutDto getDishById(UUID id);
    DishOutDto createDish(DishInDto dishInDto);

}
