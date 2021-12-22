package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import javax.ws.rs.core.Response;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen/menu")
public class MenuController {

  private final KitchenService service;

  public MenuController(DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public MenuDto getMenu(@PathVariable("id") UUID kitchenId) {
    return service.getMenu(kitchenId);
  }

  @PostMapping(path = "/{id}", consumes = "application/json")
  public DishOutDto addMenuItem(@PathVariable("id") UUID kitchenId, @RequestBody DishInDto dtoIn) {
    return service.addDishToMenu(kitchenId, dtoIn);
  }

  @DeleteMapping("/{kitchenId}/{DishId}")
  public ResponseEntity<MenuDto> getMenu(@PathVariable("kitchenId") UUID kitchenId,
      @PathVariable("DishId") UUID DishId) {
    try {
      return new ResponseEntity(service.deleteDish(kitchenId, DishId), HttpStatus.OK);
    }catch (KitchenNotFoundException exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
