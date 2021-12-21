package nl.hu.bep3.dish.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.domain.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
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

  //region dish Crud functions
  @GetMapping("/{id}")
  public DishOutDto getDishById(@PathVariable("id") UUID id) {
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


  @PostMapping(consumes = "application/json")
  public DishOutDto createDish(@RequestBody DishInDto dishInDto){
    return dishService.createDish(dishInDto);
  }

  //endregion



}
