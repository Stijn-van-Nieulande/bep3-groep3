package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import nl.hu.bep3.dish.domain.Exceptions.InvalidIngredientException;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<Kitchen> addProduct(@PathVariable("id") UUID kitchenId, @RequestBody ProductDtoIn productDto) {
    try {
      return new ResponseEntity(service.addProduct(kitchenId, productDto), HttpStatus.OK);
    } catch (InvalidIngredientException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{id}/{ingredient}", consumes = "application/json")
  public ResponseEntity<Kitchen> addToStorage(@PathVariable("id") UUID kitchenId,
      @PathVariable("ingredient") UUID ingredientId, @RequestBody ProductDtoIn productDto) {
    System.out.println("help1");
    return new ResponseEntity(service.updateProduct(kitchenId, ingredientId, productDto), HttpStatus.OK);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Kitchen> DeleteFromStorage(@PathVariable("id") UUID kitchenId, @RequestParam UUID ingredientId) {
    try {
      System.out.println("help2");
      return new ResponseEntity(service.deleteProduct(kitchenId, ingredientId), HttpStatus.OK);
    } catch (KitchenNotFoundException exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
