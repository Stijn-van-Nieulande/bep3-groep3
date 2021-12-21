package nl.hu.bep3.dish.application.rest;

import java.util.List;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
  private final IngredientService ingredientService;

  public IngredientController(IngredientService ingredientService){
    this.ingredientService = ingredientService;
  }
  @GetMapping()
  public ResponseEntity<IngredientOutDto> getAllIngredients(){
    try {
      List<Ingredient> i = ingredientService.getAllIngredients();
      return new ResponseEntity(i, HttpStatus.OK);
    }catch (Exception exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<IngredientOutDto> createIngredient(@RequestBody IngredientInDto ingredientInDto){
    try {
      IngredientOutDto i = ingredientService.createIngredient(ingredientInDto);
      return new ResponseEntity(i, HttpStatus.OK);
    }catch (Exception exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
