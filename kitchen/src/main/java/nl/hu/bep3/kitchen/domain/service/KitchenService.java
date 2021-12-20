package nl.hu.bep3.kitchen.domain.service;

import nl.hu.bep3.kitchen.application.request.KitchenDto;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import org.bson.types.ObjectId;

import java.util.List;

public interface KitchenService {
    Kitchen createKitchen(KitchenDto dto);

    Kitchen updateKitchen(KitchenDto kitchenDto, ObjectId kitchenId);

    List<OrderDto> getAllOrders(ObjectId kitchenId);

    OrderDto acceptOrder(ObjectId orderId, ObjectId kitchenId);

    void rejectOrder(ObjectId orderId, ObjectId kitchenId);

    void setStatus(ObjectId orderId, ObjectId kitchenId, OrderStatus status);

    StockDto getStock(ObjectId kitchenId);

    void getMenu(ObjectId kitchenId);
}
