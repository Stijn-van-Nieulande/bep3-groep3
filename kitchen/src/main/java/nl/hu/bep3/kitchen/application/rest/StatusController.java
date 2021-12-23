package nl.hu.bep3.kitchen.application.rest;

import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen/status")
public class StatusController {

  private final KitchenService service;

  public StatusController(DomainKitchenService service) {
    this.service = service;
  }

  public Response getAllOrders(UUID kitchenId) {
    List<OrderDto> orders = service.getAllOrders(kitchenId);

    return Response
        .status(Response.Status.OK)
        .entity(orders)
        .build();
  }

  public Response acceptOrder(UUID orderId, UUID kitchenId) {
    OrderDto order = service.acceptOrder(orderId, kitchenId);

    return Response
        .status(Response.Status.OK)
        .entity(order)
        .build();
  }

  public Response rejectOrder(UUID orderId, UUID kitchenId) {
    service.rejectOrder(orderId, kitchenId);

    return Response
        .status(Response.Status.OK)
        .entity("")
        .build();
  }

  public Response setOrderStatus(UUID orderId, UUID kitchenId, OrderStatus status) {
    service.setStatus(orderId, kitchenId, status);

    return Response
        .status(Response.Status.OK)
        .entity("")
        .build();
  }
}
