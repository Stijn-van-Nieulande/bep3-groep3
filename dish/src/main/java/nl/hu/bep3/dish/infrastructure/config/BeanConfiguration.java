package nl.hu.bep3.dish.infrastructure.config;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;
import nl.hu.bep3.dish.domain.service.DishApplicationService;
import nl.hu.bep3.dish.domain.service.DomainDishApplicationService;
import nl.hu.bep3.dish.infrastructure.repository.SpringDataMongoDishRepository;
import nl.hu.bep3.dish.infrastructure.repository.SpringDataMongoIngredientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackageClasses = DishApplication.class)
public class BeanConfiguration {
    @Bean
    DishApplicationService dishService(final DishRepository dishRepository, final IngredientRepository ingredientRepository) {
        return new DomainDishApplicationService(dishRepository, ingredientRepository);
    }
}
