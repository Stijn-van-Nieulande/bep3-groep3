package nl.hu.bep3.dish.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.Exceptions.InvalidIngredientException;
import nl.hu.bep3.dish.domain.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishController {

  private final DishService dishService;

  public DishController(DishService dishService) {
    this.dishService = dishService;
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

  //region dish Crud functions
  @GetMapping("/{id}")
  public ResponseEntity<DishOutDto> getDishById(@PathVariable("id") UUID id) {
    try {
      DishOutDto dish = new DishOutDto(dishService.getDishById(id));
      return new ResponseEntity(dish, HttpStatus.OK);
    } catch (DishNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<DishOutDto> createDish(@RequestBody DishInDto dishInDto) {
    try {
      DishOutDto dish = new DishOutDto(dishService.createDish(dishInDto));
      return new ResponseEntity(dish, HttpStatus.OK);
    } catch (InvalidIngredientException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{id}", consumes = "application/json")
  public ResponseEntity<DishOutDto> updateDish(@PathVariable("id") UUID id,
      @RequestBody DishInDto dishInDto) {
    try {
      DishOutDto dish = new DishOutDto(dishService.updateDish(id, dishInDto));
      return new ResponseEntity(dish, HttpStatus.OK);
    } catch (DishNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable("id") UUID id) {
    try {
      dishService.deleteDish(id);
      return new ResponseEntity(HttpStatus.OK);
    } catch (DishNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //endregion


}
