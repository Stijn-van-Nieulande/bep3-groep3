package nl.hu.bep3.dish.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.Exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.service.IngredientService;
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
@RequestMapping("/ingredient")
public class IngredientController {

  private final IngredientService ingredientService;

  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @GetMapping()
  public ResponseEntity<IngredientOutDto> getAllIngredients() {
    try {
      List<Ingredient> i = ingredientService.getAllIngredients();
      return new ResponseEntity(i, HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<IngredientOutDto> getIngredientById(@PathVariable UUID id) {
    try {
      IngredientOutDto ingredient = new IngredientOutDto(ingredientService.getIngredientById(id));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (IngredientNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<IngredientOutDto> createIngredient(
      @RequestBody IngredientInDto ingredientInDto) {
    try {
      IngredientOutDto ingredient = new IngredientOutDto(
          ingredientService.createIngredient(ingredientInDto));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{id}", consumes = "application/json")
  public ResponseEntity<IngredientOutDto> updateIngredient(@PathVariable("id") UUID id,
      @RequestBody IngredientInDto ingredientInDto) {
    try {
      IngredientOutDto ingredient = new IngredientOutDto(
          ingredientService.updateIngredient(id, ingredientInDto));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIngredient(@PathVariable("id") UUID id) {
    try {
      ingredientService.deleteIngredient(id);
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
