package nl.hu.bep3.management.application.rest;

import java.util.UUID;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.management.proxy.KitchenServiceProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StorageController {
  private final KitchenServiceProxy kitchenServiceProxy;

  public StorageController(final KitchenServiceProxy kitchenServiceProxy) {
    this.kitchenServiceProxy = kitchenServiceProxy;
  }

  @GetMapping("/{kitchenId}")
  public StockDto getStock(@PathVariable("kitchenId") final UUID kitchenId) {
    return this.kitchenServiceProxy.getStock(kitchenId);
  }

  @PatchMapping("/{kitchenId}")
  public StockDto updateStock(
      @PathVariable("kitchenId") final UUID kitchenId,
      @RequestBody final IngredientDto ingredientDto) {
    return this.kitchenServiceProxy.updateStock(kitchenId, ingredientDto);
  }
}
