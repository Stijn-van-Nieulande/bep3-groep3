package nl.hu.bep3.order.Aplication.rest;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.Aplication.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.service.DomainOrderService;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  //Als customer wil ik mijn gekozen gerechten kunnen bestellen
  @PostMapping("/makeOrder")
  public Order placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
    return this.orderService.placeNewOrder(orderRequestDTO);
  }

  //Als customer wil ik de status van mijn order kunnen zien
  @GetMapping("/status/{id}")
  public Status getOrderStatus(@PathVariable UUID id) {
    Order order = this.orderService.getOrderById(id);
    return order.getStatus();
  }

  //Als customer wil ik een review kunnen doen als mijn order gedelivered/opgehaald  is. (0/5 + message)
  @PutMapping("/review/{id}")
  public ReviewResponseDTO setReview(@PathVariable UUID id,
      @RequestBody ReviewRequestDTO reviewRequestDTO) {
    return this.orderService.setReview(id, reviewRequestDTO);
  }

  @PutMapping("/status/{id}")
  public void setStatus(@PathVariable UUID id, @RequestBody String status) {
    this.orderService.setStatus(id, status);
  }


  @DeleteMapping("/{id}")
  public HttpStatus deleteOrder(@PathVariable UUID id) {
    this.orderService.deleteOrder(id);
    return HttpStatus.OK;
  }

  //Als customer wil ik mijn orders in kunnen zien.
  @GetMapping("/orders")
  public List<Order> getOrdersFromCustomer(@PathVariable UUID customerId) {
    return this.orderService.getOrdersFromCustomer(customerId);
  }

  //Als order customer Wil ik dat het juiste bedrag wordt berekend
  @GetMapping("/amount")
  public Float getAmount(@PathVariable UUID id) {
    return this.orderService.getAmount(id);
  }
}
