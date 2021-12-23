package nl.hu.bep3.dish.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.exceptions.IngredientNotFoundException;
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

  public IngredientController(final IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @GetMapping()
  public ResponseEntity<IngredientOutDto> getAllIngredients() {
    try {
      final List<Ingredient> i = this.ingredientService.getAllIngredients();
      return new ResponseEntity(i, HttpStatus.OK);
    } catch (final Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{ingredientId}")
  public ResponseEntity<IngredientOutDto> getIngredientById(@PathVariable final UUID ingredientId) {
    try {
      final IngredientOutDto ingredient =
          new IngredientOutDto(this.ingredientService.getIngredientById(ingredientId));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (final IngredientNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    } catch (final Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<IngredientOutDto> createIngredient(
      @RequestBody final IngredientInDto ingredientInDto) {
    try {
      final IngredientOutDto ingredient =
          new IngredientOutDto(this.ingredientService.createIngredient(ingredientInDto));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (final Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{ingredientId}", consumes = "application/json")
  public ResponseEntity<IngredientOutDto> updateIngredient(
      @PathVariable("ingredientId") final UUID ingredientId,
      @RequestBody final IngredientInDto ingredientInDto) {
    try {
      final IngredientOutDto ingredient =
          new IngredientOutDto(
              this.ingredientService.updateIngredient(ingredientId, ingredientInDto));
      return new ResponseEntity(ingredient, HttpStatus.OK);
    } catch (final Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{ingredientId}")
  public ResponseEntity<Void> deleteIngredient(
      @PathVariable("ingredientId") final UUID ingredientId) {
    try {
      this.ingredientService.deleteIngredient(ingredientId);
      return new ResponseEntity(HttpStatus.OK);
    } catch (final Exception exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
