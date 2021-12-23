package nl.hu.bep3.kitchen.application.rest;

import java.util.List;
import java.util.UUID;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen/status")
public class StatusController {

  private final KitchenService service;

  public StatusController(DomainKitchenService service) {
    this.service = service;
  }

  @GetMapping("/{kitchenId}")
  public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable("kitchenId") UUID kitchenId) {
    try{
      return new ResponseEntity(service.getAllOrders(kitchenId), HttpStatus.OK);
    }catch (KitchenNotFoundException exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PatchMapping("/{kitchenId}/{orderId}")
  public ResponseEntity<OrderDto> setOrderStatus(@PathVariable("orderId") UUID orderId, @PathVariable("kitchenId") UUID kitchenId, @RequestParam("status") String status) {
    try{
      return new ResponseEntity(service.setStatus(orderId, kitchenId, status), HttpStatus.OK);
    }catch (KitchenNotFoundException | OrderNotFoundException exception){
      return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
