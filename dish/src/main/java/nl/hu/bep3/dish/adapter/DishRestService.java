package nl.hu.bep3.dish.adapter;

import nl.hu.bep3.dish.adapter.dto.DishOutDto;
import nl.hu.bep3.dish.application.DishApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dish")
public class DishRestService {
  private final DishApplicationService dishApplicationService;

  public DishRestService(DishApplicationService dishApplicationService) {
    this.dishApplicationService = dishApplicationService;
  }

  @GetMapping()
  public DishOutDto getDishById(@RequestParam Long id) {
    return dishApplicationService.getDishById(id);
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
  public DishOutDto createDish(){
    return null;
  }
}
