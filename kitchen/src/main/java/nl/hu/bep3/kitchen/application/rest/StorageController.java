package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.domain.exceptions.InvalidIngredientException;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kitchen/stock")
public class StorageController {

  private final KitchenService service;

  public StorageController(final DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{kitchenId}")
  public StockDtoOut showStorage(@PathVariable("kitchenId") final UUID kitchenId) {
    try {
      return this.service.getStock(kitchenId);
    } catch (final KitchenNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }

  @PostMapping("/{kitchenId}")
  public Kitchen addProduct(
      @PathVariable("kitchenId") final UUID kitchenId, @RequestBody final ProductDtoIn productDto) {
    try {
      return this.service.addProduct(kitchenId, productDto);
    } catch (final InvalidIngredientException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PatchMapping(path = "/{kitchenId}/{ingredient}", consumes = "application/json")
  public Kitchen updateStock(
      @PathVariable("kitchenId") final UUID kitchenId,
      @PathVariable("ingredient") final UUID ingredientId,
      @RequestBody final ProductDtoIn productDto) {
    System.out.println("help1");
    return this.service.updateProduct(kitchenId, ingredientId, productDto);
  }

  @DeleteMapping(path = "/{kitchenId}")
  public Kitchen DeleteFromStorage(
      @PathVariable("kitchenId") final UUID kitchenId, @RequestParam final UUID ingredientId) {
    try {
      System.out.println("help2");
      return this.service.deleteProduct(kitchenId, ingredientId);
    } catch (final KitchenNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }
}
