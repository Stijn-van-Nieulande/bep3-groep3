package nl.hu.bep3.management.proxy;

import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.Kitchen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kitchen-service", url = "${app.feign.kitchen.url}")
public interface KitchenServiceProxy {
  @GetMapping("/kitchen/stock/{kitchenId}")
  StockDto getStock(@PathVariable("kitchenId") UUID kitchenId);

  @PatchMapping("/kitchen/stock/{kitchenId}/{ingredient}")
  Kitchen updateStock(
      @PathVariable("kitchenId") UUID kitchenId,
      @PathVariable("ingredient") UUID ingredientId,
      @RequestBody ProductDtoIn productDto);

  @GetMapping("/kitchen/menu/{kitchenId}")
  MenuDto getMenu(@PathVariable("kitchenId") UUID kitchenId);

  @PostMapping(path = "/kitchen/menu/{kitchenId}", consumes = "application/json")
  DishOutDto addMenuItem(@PathVariable("kitchenId") UUID kitchenId, @RequestBody DishInDto dtoIn);

  @DeleteMapping("/kitchen/menu/{kitchenId}/{dishId}")
  MenuDto deleteMenu(
      @PathVariable("kitchenId") UUID kitchenId, @PathVariable("dishId") UUID dishId);
}
