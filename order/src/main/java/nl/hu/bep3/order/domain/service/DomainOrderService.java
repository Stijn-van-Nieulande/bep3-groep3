package nl.hu.bep3.order.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.exceptions.CustomerNotFoundException;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.order.application.request.OrderRequestDTO;
import nl.hu.bep3.order.application.request.OrderRequestDTO.DishOrderDto;
import nl.hu.bep3.order.application.request.ReviewRequestDTO;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
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

  private OrderRepository orderRepository;
  private ReviewRepository reviewRepository;
  private QueueSender queueSender;
  private DishServiceProxy dishProxy;

  public DomainOrderService(
      OrderRepository orderRepository,
      ReviewRepository reviewRepository,
      QueueSender queueSender,
      DishServiceProxy dishProxy) {
    this.queueSender = queueSender;
    this.orderRepository = orderRepository;
    this.reviewRepository = reviewRepository;
    this.dishProxy = dishProxy;
  }

  @Override
  public void setStatus(UUID id, String status) {
    System.out.println("I'M HERE YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET");
    Order order = getOrderById(id);
    System.out.println(order.getStatus());
    order.setStatus(status);
    System.out.println(order.getStatus());
    orderRepository.save(order);
  }

  @Override
  public OrderResponseDTO placeNewOrder(OrderRequestDTO orderRequestDTO) {
    System.out.println("place new order in domein order service");

    Customer customer = this.queueSender.getCustomer(orderRequestDTO.customerId);
    if (customer == null) {
      throw new CustomerNotFoundException(orderRequestDTO.customerId);
    }
    boolean deliver = orderRequestDTO.deliver;
    List<DishOrderDto> dishOrderDtoList = orderRequestDTO.dishOrders;
    String customerMessage = orderRequestDTO.customerMessage;
    UUID kitchenId = orderRequestDTO.kitchenId;

    List<DishOrder> dishOrderList = new ArrayList<>();

    for (DishOrderDto dishOrderDto : dishOrderDtoList) {
      DishOutDto dishData = dishProxy.getDishById(dishOrderDto.dishId).getBody();
      dishOrderList.add(
          new DishOrder(dishOrderDto.amountOfDish, dishOrderDto.dishId, dishData.price));
    }

    Order order =
        this.orderRepository.save(
            new Order(customer, deliver, dishOrderList, customerMessage, kitchenId));

    System.out.println(order);

    this.queueSender.sendOrder(order);

    return new OrderResponseDTO(order);
  }

  @Override
  public void sendOrder(Order order) {
    this.queueSender.sendOrder(order);
  }

  @Override
  public Page<Review> getReviewsPaginated(final Pageable pageable) {
    return this.reviewRepository.findAllPaginated(pageable);
  }

  @Override
  public Order getOrderById(UUID orderId) {
    return this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
  }

  @Override
  public ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO) {
    Order order = getOrderById(id);
    Review review =
        this.reviewRepository.save(
            new Review(reviewRequestDTO.reviewMessage, reviewRequestDTO.rating));

    order.setReview(review);
    order = this.orderRepository.save(order);

    System.out.println("KIPPPETJESSSSS »»»»»»»»»»»»»»»»»»» " + order.getReview().getReviewId());

    return new ReviewResponseDTO(order.getReview());
  }

  @Override
  public void deleteOrder(UUID id) {
    this.orderRepository.deleteById(id);
  }

  public List<OrderResponseDTO> getOrdersFromCustomer(UUID customerId) {
    List<Order> orderList = this.orderRepository.findOrdersFromCustomer(customerId);
    List<OrderResponseDTO> responseList = new ArrayList<OrderResponseDTO>();

    for (Order order : orderList) {
      responseList.add(new OrderResponseDTO(order));
    }

    return responseList;
  }

  public double getAmount(UUID id) {
    Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFound(id));
    return order.calcTotPrice();
  }
}
