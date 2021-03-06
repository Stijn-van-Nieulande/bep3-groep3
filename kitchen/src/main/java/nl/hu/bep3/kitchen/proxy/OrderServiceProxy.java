package nl.hu.bep3.kitchen.proxy;

import java.util.UUID;
import nl.hu.bep3.order.application.response.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Order-service", url = "${app.feign.order.url}")
public interface OrderServiceProxy {

  @PutMapping("order/status/{id}")
  void setStatus(@PathVariable UUID id, @RequestBody String status);

  @GetMapping("/order/order/{id}")
  OrderResponseDto getOrderById(@PathVariable("id") UUID orderId);
}
