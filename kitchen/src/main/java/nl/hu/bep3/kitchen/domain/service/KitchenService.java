package nl.hu.bep3.kitchen.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import org.springframework.http.ResponseEntity;

public interface KitchenService {

  Kitchen createKitchen(KitchenDtoIn dto);

  Kitchen updateKitchen(KitchenDtoIn kitchenDtoIn, UUID kitchenId);

  List<OrderDto> getAllOrders(UUID kitchenId);

  OrderDto acceptOrder(UUID orderId, UUID kitchenId);

  void rejectOrder(UUID orderId, UUID kitchenId);

  void setStatus(UUID orderId, UUID kitchenId, OrderStatus status);

  StockDtoOut getStock(UUID kitchenId);

  MenuDto getMenu(UUID kitchenId);

  DishOutDto addDishToMenu(UUID kitchenId, DishInDto dto);

  List<Kitchen> findAll();

  Kitchen findById(UUID id);

  MenuDto deleteDish(UUID kitchenId, UUID dishId);

  Kitchen addProduct(UUID kitchenId, ProductDtoIn dto);

  void deleteKitchen(UUID kitchenId);

  Kitchen updateProduct(UUID kitchenId, UUID ingredientId,
      ProductDtoIn productDto);

  Kitchen deleteProduct(UUID kitchenId, UUID ingredientId);
}
