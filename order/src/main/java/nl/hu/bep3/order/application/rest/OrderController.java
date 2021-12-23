package nl.hu.bep3.order.application.rest;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.application.request.OrderRequestDto;
import nl.hu.bep3.order.application.request.ReviewRequestDto;
import nl.hu.bep3.order.application.response.OrderResponseDto;
import nl.hu.bep3.order.application.response.ReviewResponseDto;
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
  public OrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/order/{orderId}")
  public OrderResponseDto getOrderById(@PathVariable("orderId") final UUID orderId) {
    final Order order = this.orderService.getOrderById(orderId);
    return new OrderResponseDto(order);
  }

  // Als customer wil ik mijn gekozen gerechten kunnen bestellen
  @PostMapping("/makeOrder")
  public OrderResponseDto placeOrder(@RequestBody final OrderRequestDto orderRequestDto) {
    return this.orderService.placeNewOrder(orderRequestDto);
  }

  // Als customer wil ik de status van mijn order kunnen zien
  @GetMapping("/status/{orderId}")
  public Status getOrderStatus(@PathVariable final UUID orderId) {
    final Order order = this.orderService.getOrderById(orderId);
    return order.getStatus();
  }

  @GetMapping("/review")
  public Page<Review> getReviews(@ParameterObject final Pageable pageable) {
    return this.orderService.getReviewsPaginated(pageable);
  }

  // Als customer wil ik een review kunnen doen als mijn order gedelivered/opgehaald is.
  // (0/5 + message)
  @PutMapping("/review/{orderId}")
  public ReviewResponseDto setReview(
      @PathVariable final UUID orderId, @RequestBody final ReviewRequestDto reviewRequestDto) {
    return this.orderService.setReview(orderId, reviewRequestDto);
  }

  @PutMapping("/status/{orderId}")
  public void setStatus(@PathVariable final UUID orderId, @RequestBody final String status) {
    this.orderService.setStatus(orderId, status);
  }

  @DeleteMapping("/{orderId}")
  public HttpStatus deleteOrder(@PathVariable final UUID orderId) {
    this.orderService.deleteOrder(orderId);
    return HttpStatus.OK;
  }

  // Als customer wil ik mijn orders in kunnen zien.
  @GetMapping("/{customerId}")
  public List<OrderResponseDto> getOrdersFromCustomer(@PathVariable final UUID customerId) {
    return this.orderService.getOrdersFromCustomer(customerId);
  }

  // Als order customer Wil ik dat het juiste bedrag wordt berekend
  @GetMapping("/amount/{orderId}")
  public double getAmount(@PathVariable final UUID orderId) {
    return this.orderService.getAmount(orderId);
  }

  //  @PostMapping("/send/{id}")
  //  public void sendOrderToKitchen(@PathVariable UUID id) {
  //    Order order = this.orderService.getOrderById(id);
  //    this.orderService.sendOrder(order);
  //  }
}
