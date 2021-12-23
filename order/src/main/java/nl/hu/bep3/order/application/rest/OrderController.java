package nl.hu.bep3.order.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.application.request.OrderRequestDTO;
import nl.hu.bep3.order.application.request.ReviewRequestDTO;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.service.OrderService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/order/{orderId}")
  public OrderResponseDTO getOrderById(@PathVariable("orderId") UUID orderId){
    Order order = this.orderService.getOrderById(orderId);
    return new OrderResponseDTO(order);
  }

  // Als customer wil ik mijn gekozen gerechten kunnen bestellen
  @PostMapping("/makeOrder")
  public OrderResponseDTO placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
    return this.orderService.placeNewOrder(orderRequestDTO);
  }

  // Als customer wil ik de status van mijn order kunnen zien
  @GetMapping("/status/{orderId}")
  public Status getOrderStatus(@PathVariable UUID orderId) {
    Order order = this.orderService.getOrderById(orderId);
    return order.getStatus();
  }

  @GetMapping("/review")
  public Page<Review> getReviews(@ParameterObject final Pageable pageable) {
    return this.orderService.getReviewsPaginated(pageable);
  }

  // Als customer wil ik een review kunnen doen als mijn order gedelivered/opgehaald is.
  // (0/5 + message)
  @PutMapping("/review/{orderId}")
  public ReviewResponseDTO setReview(
      @PathVariable UUID orderId, @RequestBody ReviewRequestDTO reviewRequestDTO) {
    return this.orderService.setReview(orderId, reviewRequestDTO);
  }

  @PutMapping("/status/{orderId}")
  public void setStatus(@PathVariable UUID orderId, @RequestBody String status) {
    this.orderService.setStatus(orderId, status);
  }

  @DeleteMapping("/{orderId}")
  public HttpStatus deleteOrder(@PathVariable UUID orderId) {
    this.orderService.deleteOrder(orderId);
    return HttpStatus.OK;
  }

  // Als customer wil ik mijn orders in kunnen zien.
  @GetMapping("/{customerId}")
  public List<OrderResponseDTO> getOrdersFromCustomer(@PathVariable UUID customerId) {
    return this.orderService.getOrdersFromCustomer(customerId);
  }

  // Als order customer Wil ik dat het juiste bedrag wordt berekend
  @GetMapping("/amount/{orderId}")
  public double getAmount(@PathVariable UUID orderId) {
    return this.orderService.getAmount(orderId);
  }

//  @PostMapping("/send/{id}")
//  public void sendOrderToKitchen(@PathVariable UUID id) {
//    Order order = this.orderService.getOrderById(id);
//    this.orderService.sendOrder(order);
//  }
}
