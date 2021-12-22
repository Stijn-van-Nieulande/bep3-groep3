package nl.hu.bep3.dish.infrastructure.config;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;
import nl.hu.bep3.dish.domain.service.DishService;
import nl.hu.bep3.dish.domain.service.DomainDishService;
import nl.hu.bep3.dish.domain.service.DomainIngredientService;
import nl.hu.bep3.dish.domain.service.IngredientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DishApplication.class)
public class BeanConfiguration {

  @Bean
  IngredientService ingredientService(final IngredientRepository ingredientRepository) {
    return new DomainIngredientService(ingredientRepository);
  }

  @Bean
  DishService dishService(final DishRepository dishRepository,
      final IngredientService ingredientService) {
    return new DomainDishService(dishRepository, ingredientService);
  }
}
