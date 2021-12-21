package nl.hu.bep3.order.domain.service;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Payment;
import nl.hu.bep3.order.domain.ValueObjects.DishOrder;
import nl.hu.bep3.order.domain.repository.OrderRepository;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.exeption.OrderNotFound;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;


public class DomainOrderService implements OrderService {
    private OrderRepository orderRepository;
    private Order order;

    @Override
    public DomainOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void setStatus(UUID id, String status){
        try {
            order = getOrderById(id);
            order.setStatus(status);
        }catch (Exception e){
        }
    }

    @Override
    public Order placeNewOrder(OrderRequestDTO orderRequestDTO){
      String adres = orderRequestDTO.getAdres();
      Customer customer = orderRequestDTO.getCustomer();
      Payment payment = orderRequestDTO.getPayment();
      boolean deliver = orderRequestDTO.isDeliver();
      String paymentMethod = orderRequestDTO.getPaymentMethod();
      List<DishOrder> dishOrderList = orderRequestDTO.getDishOrders();

      Order order = new Order(adres, customer, payment, deliver, paymentMethod, dishOrderList);
      return order;
    }

    @Override
      public void completeOrder(UUID id) {

      }

    @Override
    public void deleteProduct(UUID id) {

    }

    @Override
    public Order getOrderById(UUID orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound(orderId));
    }

    @Override
    public ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO){
        order = getOrderById(id);
        return order.setReview(reviewRequestDTO.message, reviewRequestDTO.rating);
    }

    @Override
    public HttpStatus deleteOrder(UUID id) {
      order = getOrderById(id);
      return order.delete()
          .orElseThrow(() -> new Exception());
    }
}
