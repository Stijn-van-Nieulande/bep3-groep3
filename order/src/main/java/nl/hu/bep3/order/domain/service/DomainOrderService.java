package nl.hu.bep3.order.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.application.request.OrderRequestDTO;
import nl.hu.bep3.order.application.request.ReviewRequestDTO;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.exception.OrderNotFound;
import nl.hu.bep3.order.domain.repository.OrderRepository;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import nl.hu.bep3.order.infrastructure.rabbitmq.QueueSender;

public class DomainOrderService implements OrderService {

  private OrderRepository orderRepository;
  private QueueSender queueSender;
  private Order order;

  public DomainOrderService(OrderRepository orderRepository, QueueSender queueSender) {
    this.queueSender = queueSender;
    this.orderRepository = orderRepository;
  }

  @Override
  public void setStatus(UUID id, String status) {
    order = getOrderById(id);
    order.setStatus(status);
  }

  @Override
  public OrderResponseDTO placeNewOrder(OrderRequestDTO orderRequestDTO) {
    Customer customer = this.queueSender.getCustomer(orderRequestDTO.customerId);

    boolean deliver = orderRequestDTO.deliver;
    List<DishOrder> dishOrderList = orderRequestDTO.dishOrders;
    String customerMessage = orderRequestDTO.customerMessage;
    UUID kitchenId = orderRequestDTO.kitchenId;

    Order order =
        this.orderRepository.save(
            new Order(customer, deliver, dishOrderList, customerMessage, kitchenId));

    return new OrderResponseDTO(order);
  }

  @Override
  public void completeOrder(UUID id) {
    // TODO: Implement me
  }

  @Override
  public void deleteProduct(UUID id) {
    // TODO: Implement me
  }

  @Override
  public Order getOrderById(UUID orderId) {
    return this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
  }

  @Override
  public ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO) {
    order = getOrderById(id);
    return order.setReview(reviewRequestDTO.reviewMessage, reviewRequestDTO.rating);
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

  public Float getAmount(UUID id) {
    order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFound(id));
    return order.calcTotPrice();
  }
}
