package nl.hu.bep3.order.domain.repository;

import nl.hu.bep3.order.domain.valueobjects.DishOrder;

public interface DishOrderRepository {

  DishOrder save(DishOrder dishOrder);
}
