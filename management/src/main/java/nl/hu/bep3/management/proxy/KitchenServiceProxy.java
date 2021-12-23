package nl.hu.bep3.management.proxy;

import java.util.UUID;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kitchen-service", url = "${app.feign.kitchen.url}")
public interface KitchenServiceProxy {
  @GetMapping("/stock/{kitchenId}")
  StockDto getStock(@PathVariable("kitchenId") UUID kitchenId);

  @PatchMapping("/stock/{kitchenId}")
  StockDto updateStock(
      @PathVariable("kitchenId") UUID kitchenId, @RequestBody IngredientDto ingredientDto);
}
