package nl.hu.bep3.management.proxy;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kitchen-service", url = "${app.feign.kitchen.url}")
public interface KitchenServiceProxy {
  @GetMapping("/stock/{kitchenId}")
  Response retrieveStock(@PathVariable("kitchenId") Long kitchenId);

  @PatchMapping("/stock/{kitchenId}")
  Response updateStock(@PathVariable("kitchenId") Long kitchenId, @RequestBody String to);
}
