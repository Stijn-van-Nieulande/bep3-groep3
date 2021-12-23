package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kitchen/menu")
public class MenuController {

  private final KitchenService service;

  public MenuController(final DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{kitchenId}")
  public MenuDto getMenu(@PathVariable("kitchenId") final UUID kitchenId) {
    return this.service.getMenu(kitchenId);
  }

  @PostMapping(path = "/{kitchenId}", consumes = "application/json")
  public DishOutDto addMenuItem(
      @PathVariable("kitchenId") final UUID kitchenId, @RequestBody final DishInDto dtoIn) {
    return this.service.addDishToMenu(kitchenId, dtoIn);
  }

  @DeleteMapping("/{kitchenId}/{DishId}")
  public MenuDto deleteMenu(
      @PathVariable("kitchenId") final UUID kitchenId, @PathVariable("DishId") final UUID dishId) {
    try {
      return this.service.deleteDish(kitchenId, dishId);
    } catch (final KitchenNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }
}
