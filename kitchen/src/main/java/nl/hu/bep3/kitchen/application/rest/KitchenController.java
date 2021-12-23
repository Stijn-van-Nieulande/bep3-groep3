package nl.hu.bep3.kitchen.application.rest;

import java.util.UUID;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {

  private final KitchenService service;

  public KitchenController(DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping()
  public ResponseEntity<Kitchen> getAllKitchens() {
    try {
      return new ResponseEntity(service.findAll(), HttpStatus.OK);
    } catch (InvalidKitchenException | NullPointerException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<Kitchen> createKitchen(@RequestBody KitchenDtoIn kitchenDtoIn) {
    try {
      return new ResponseEntity(service.createKitchen(kitchenDtoIn), HttpStatus.OK);
    } catch (InvalidKitchenException | NullPointerException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping(path = "/{kitchenId}", consumes = "application/json")
  public ResponseEntity<Kitchen> updateKitchen(@PathVariable("kitchenId") UUID kitchenId,
      @RequestBody KitchenDtoIn kitchenDtoIn) {
    try {
      return new ResponseEntity(service.updateKitchen(kitchenDtoIn, kitchenId), HttpStatus.OK);
    } catch (InvalidKitchenException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(path = "/{kitchenId}")
  public ResponseEntity deleteKitchen(@PathVariable("kitchenId") UUID kitchenId) {
    try {
      service.deleteKitchen(kitchenId);
      return new ResponseEntity(HttpStatus.OK);
    } catch (KitchenNotFoundException exception) {
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
