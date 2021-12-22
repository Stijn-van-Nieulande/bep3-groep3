package nl.hu.bep3.kitchen.proxy;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Dish-service", url = "${app.feign.dish.url}")
public interface DishServiceProxy {

  @PostMapping("/ingredient")
  ResponseEntity<IngredientOutDto> createIngredient(@RequestBody IngredientInDto ingredientInDto);

  @PatchMapping("/ingredient/{id}")
  ResponseEntity<IngredientOutDto> updateIngredient(@PathVariable("id") UUID id,
      @RequestBody IngredientInDto ingredientInDto);

  @DeleteMapping("/ingredient/{id}")
  ResponseEntity<IngredientOutDto> deleteIngredient(@PathVariable("id") UUID id);


  @PostMapping("/ingredient")
  ResponseEntity<DishOutDto> createDish(@RequestBody DishInDto dishInDto);

  @PatchMapping("/ingredient/{id}")
  ResponseEntity<DishOutDto> updateDish(@PathVariable("id") UUID id,
      @RequestBody DishInDto dishInDto);

  @DeleteMapping("/ingredient/{id}")
  ResponseEntity<DishOutDto> deleteDish(@PathVariable("id") UUID id);
}
