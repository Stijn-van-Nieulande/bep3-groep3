package nl.hu.bep3.kitchen.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {

  private final KitchenService service;

  public KitchenController(final DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping()
  public List<Kitchen> getAllKitchens() {
    try {
      return this.service.findAll();
    } catch (final InvalidKitchenException | NullPointerException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PostMapping(consumes = "application/json")
  public Kitchen createKitchen(@RequestBody final KitchenDtoIn kitchenDtoIn) {
    try {
      return this.service.createKitchen(kitchenDtoIn);
    } catch (final InvalidKitchenException | NullPointerException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PatchMapping(path = "/{kitchenId}", consumes = "application/json")
  public Kitchen updateKitchen(
      @PathVariable("kitchenId") final UUID kitchenId,
      @RequestBody final KitchenDtoIn kitchenDtoIn) {
    try {
      return this.service.updateKitchen(kitchenDtoIn, kitchenId);
    } catch (final InvalidKitchenException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }

  @DeleteMapping(path = "/{kitchenId}")
  public void deleteKitchen(@PathVariable("kitchenId") final UUID kitchenId) {
    try {
      this.service.deleteKitchen(kitchenId);
    } catch (final KitchenNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }
}
