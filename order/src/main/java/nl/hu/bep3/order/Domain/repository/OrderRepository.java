package nl.hu.bep3.order.domain.repository;

import nl.hu.bep3.order.domain.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Optional<Order> findById(UUID id);

  Order save(Order employee);

  void deleteById(UUID uuid);

  List<Order> findOrdersFromCustomer(UUID customerId);
}
