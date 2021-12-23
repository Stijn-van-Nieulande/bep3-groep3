package nl.hu.bep3.dish.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.domain.exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.exceptions.InvalidIngredientException;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/dish")
public class DishController {

  private final DishService dishService;

  public DishController(final DishService dishService) {
    this.dishService = dishService;
  }

  // region dish Crud functions
  @GetMapping("/{dishId}")
  public DishOutDto getDishById(@PathVariable("dishId") final UUID dishId) {
    try {
      final DishOutDto dish = new DishOutDto(this.dishService.getDishById(dishId));
      return dish;
    } catch (final DishNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PostMapping(consumes = "application/json")
  public DishOutDto createDish(@RequestBody final DishInDto dishInDto) {
    try {
      final DishOutDto dish = new DishOutDto(this.dishService.createDish(dishInDto));
      return dish;
    } catch (final InvalidIngredientException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PatchMapping(path = "/{dishId}", consumes = "application/json")
  public ResponseEntity<DishOutDto> updateDish(
      @PathVariable("dishId") final UUID dishId, @RequestBody final DishInDto dishInDto) {
    try {
      final DishOutDto dish = new DishOutDto(this.dishService.updateDish(dishId, dishInDto));
      return new ResponseEntity(dish, HttpStatus.OK);
    } catch (final DishNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @DeleteMapping("/{dishId}")
  public void deleteDish(@PathVariable("dishId") final UUID dishId) {
    try {
      this.dishService.deleteDish(dishId);
    } catch (final DishNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }
  // endregion
}
