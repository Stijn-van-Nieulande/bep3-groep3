package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.Exceptions.InvalidIngredientException;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen/stock")
public class StorageController {

  private final KitchenService service;

  public StorageController(DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<StockDtoOut> showStorage(@PathVariable("id") UUID kitchenId) {
    try {
      return new ResponseEntity(service.getStock(kitchenId), HttpStatus.OK);
    } catch (KitchenNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/{id}")
  public ResponseEntity<IngredientOutDto> addProduct(@PathVariable("id") UUID kitchenId,
      @RequestBody ProductDtoIn productDto) {
    try {
      return service.addProduct(kitchenId, productDto);
    } catch (InvalidIngredientException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{id}/{ingredient}", consumes = "application/json")
  public ResponseEntity<IngredientOutDto> addToStorage(@PathVariable("id") UUID kitchenId,
      @PathVariable("ingredient") UUID ingredientId, @RequestBody IngredientInDto productDto) {
    return service.updateProduct(kitchenId, ingredientId, productDto);
  }
}
