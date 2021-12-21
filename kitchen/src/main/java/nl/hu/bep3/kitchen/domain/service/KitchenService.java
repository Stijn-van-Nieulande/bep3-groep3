package nl.hu.bep3.kitchen.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.kitchen.application.request.KitchenDto;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;

public interface KitchenService {
    Kitchen createKitchen(KitchenDto dto);

    Kitchen updateKitchen(KitchenDto kitchenDto, UUID kitchenId);

    List<OrderDto> getAllOrders(UUID kitchenId);

    OrderDto acceptOrder(UUID orderId, UUID kitchenId);

    void rejectOrder(UUID orderId, UUID kitchenId);

    void setStatus(UUID orderId, UUID kitchenId, OrderStatus status);

    StockDto getStock(UUID kitchenId);

    void getMenu(UUID kitchenId);
}
