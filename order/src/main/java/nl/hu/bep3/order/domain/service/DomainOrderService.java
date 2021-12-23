package nl.hu.bep3.order.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.exceptions.CustomerNotFoundException;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.order.application.request.OrderRequestDto;
import nl.hu.bep3.order.application.request.OrderRequestDto.DishOrderDto;
import nl.hu.bep3.order.application.request.ReviewRequestDto;
import nl.hu.bep3.order.application.response.OrderResponseDto;
import nl.hu.bep3.order.application.response.ReviewResponseDto;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.exception.OrderNotFound;
import nl.hu.bep3.order.domain.repository.OrderRepository;
import nl.hu.bep3.order.domain.repository.ReviewRepository;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import nl.hu.bep3.order.infrastructure.rabbitmq.QueueSender;
import nl.hu.bep3.order.proxy.DishServiceProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DomainOrderService implements OrderService {

  private final OrderRepository orderRepository;
  private final ReviewRepository reviewRepository;
  private final QueueSender queueSender;
  private final DishServiceProxy dishProxy;

  public DomainOrderService(
      final OrderRepository orderRepository,
      final ReviewRepository reviewRepository,
      final QueueSender queueSender,
      final DishServiceProxy dishProxy) {
    this.queueSender = queueSender;
    this.orderRepository = orderRepository;
    this.reviewRepository = reviewRepository;
    this.dishProxy = dishProxy;
  }

  @Override
  public void setStatus(final UUID id, final String status) {
    final Order order = this.getOrderById(id);
    order.setStatus(status);
    this.orderRepository.save(order);
  }

  @Override
  public OrderResponseDto placeNewOrder(final OrderRequestDto orderRequestDto) {
    System.out.println("place new order in domein order service");

    final Customer customer = this.queueSender.getCustomer(orderRequestDto.customerId);
    if (customer == null) {
      throw new CustomerNotFoundException(orderRequestDto.customerId);
    }
    final boolean deliver = orderRequestDto.deliver;
    final List<DishOrderDto> dishOrderDtoList = orderRequestDto.dishOrders;
    final String customerMessage = orderRequestDto.customerMessage;
    final UUID kitchenId = orderRequestDto.kitchenId;

    final List<DishOrder> dishOrderList = new ArrayList<>();

    for (final DishOrderDto dishOrderDto : dishOrderDtoList) {
      final DishOutDto dishData = this.dishProxy.getDishById(dishOrderDto.dishId);
      dishOrderList.add(
          new DishOrder(dishOrderDto.amountOfDish, dishOrderDto.dishId, dishData.price));
    }

    final Order order =
        this.orderRepository.save(
            new Order(customer, deliver, dishOrderList, customerMessage, kitchenId));

    System.out.println(order);

    this.queueSender.sendOrder(order);

    return new OrderResponseDto(order);
  }

  @Override
  public void sendOrder(final Order order) {
    this.queueSender.sendOrder(order);
  }

  @Override
  public Page<Review> getReviewsPaginated(final Pageable pageable) {
    return this.reviewRepository.findAllPaginated(pageable);
  }

  @Override
  public Order getOrderById(final UUID orderId) {
    return this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
  }

  @Override
  public ReviewResponseDto setReview(final UUID id, final ReviewRequestDto reviewRequestDto) {
    Order order = this.getOrderById(id);
    final Review review =
        this.reviewRepository.save(
            new Review(reviewRequestDto.reviewMessage, reviewRequestDto.rating));

    order.setReview(review);
    order = this.orderRepository.save(order);

    return new ReviewResponseDto(order.getReview());
  }

  @Override
  public void deleteOrder(final UUID id) {
    this.orderRepository.deleteById(id);
  }

  public List<OrderResponseDto> getOrdersFromCustomer(final UUID customerId) {
    final List<Order> orderList = this.orderRepository.findOrdersFromCustomer(customerId);
    final List<OrderResponseDto> responseList = new ArrayList<OrderResponseDto>();

    for (final Order order : orderList) {
      responseList.add(new OrderResponseDto(order));
    }

    return responseList;
  }

  public double getAmount(final UUID id) {
    final Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFound(id));
    return order.calcTotPrice();
  }
}
