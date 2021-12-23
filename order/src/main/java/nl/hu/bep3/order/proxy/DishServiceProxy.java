package nl.hu.bep3.order.proxy;

import java.util.UUID;
import nl.hu.bep3.dish.application.response.DishOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dish-service", url = "${app.feign.dish.url}")
public interface DishServiceProxy {

  @GetMapping("/dish/{id}")
  DishOutDto getDishById(@PathVariable("id") UUID id);
}
