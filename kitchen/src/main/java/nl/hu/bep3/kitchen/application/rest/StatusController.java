package nl.hu.bep3.kitchen.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import nl.hu.bep3.order.application.response.OrderResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kitchen/status")
public class StatusController {

  private final KitchenService service;

  public StatusController(final DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{kitchenId}")
  public List<OrderResponseDto> getAllOrders(@PathVariable("kitchenId") final UUID kitchenId) {
    try {
      return this.service.getAllOrders(kitchenId);
    } catch (final KitchenNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }

  @PatchMapping("/{kitchenId}/{orderId}")
  public OrderDto setOrderStatus(
      @PathVariable("orderId") final UUID orderId,
      @PathVariable("kitchenId") final UUID kitchenId,
      @RequestParam("status") final String status) {
    try {
      return this.service.setStatus(orderId, kitchenId, status);
    } catch (final KitchenNotFoundException | OrderNotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
  }
}
