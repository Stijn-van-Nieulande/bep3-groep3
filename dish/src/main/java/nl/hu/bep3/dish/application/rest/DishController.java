package nl.hu.bep3.dish.application.rest;

import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.service.DishApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dish")
public class DishController {
  private final DishApplicationService dishService;

  public DishController(DishApplicationService dishService) {
    this.dishService = dishService;
  }

  @GetMapping()
  public DishOutDto getDishById(@RequestParam Long id) {
    return dishService.getDishById(id);
  }

//  idea of input json
//  {
//    name: "dishName",
//    price: 1,
//    ingredients:{
//        amount: 1,
//        amountUnit: LITER,
//        ingredient: {
//          id: 1,          als ingredient niet gevonden kan worden maak een nieuwe
//          name: "ingredient name",
//          allergies:{
//            PEANUTS,
//            MILK
//        }
//      }
//    }
//  }
  @PostMapping()
  public DishOutDto createDish(@RequestBody DishInDto dishInDto){
    return dishService.createDish(dishInDto);
  }

  @PostMapping("/ingredient")
  public ResponseEntity<IngredientOutDto> createIngredient(@RequestBody IngredientInDto ingredientInDto){
    try {
      IngredientOutDto i = dishService.createIngredient(ingredientInDto);
      return new ResponseEntity(i, HttpStatus.OK);
    }catch (Exception exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
