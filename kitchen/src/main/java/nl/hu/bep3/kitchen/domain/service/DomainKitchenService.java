package nl.hu.bep3.kitchen.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.ProductDto;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.ProductInStock;
import nl.hu.bep3.kitchen.domain.Storage;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import nl.hu.bep3.kitchen.domain.repository.KitchenRepository;
import nl.hu.bep3.kitchen.infrastructure.rabbitmq.QueueSender;
import nl.hu.bep3.kitchen.proxy.DishServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DomainKitchenService implements KitchenService {

  final KitchenRepository kitchenRepository;
  final QueueSender queueSender;
  final DishServiceProxy proxy;

  public DomainKitchenService(KitchenRepository kitchenRepository, QueueSender queueSender,
      DishServiceProxy proxy) {
    this.kitchenRepository = kitchenRepository;
    this.queueSender = queueSender;
    this.proxy = proxy;
  }

  @Override
  public List<Kitchen> findAll() {
    return kitchenRepository.findAll();
  }

  public Kitchen findById(UUID id) {
    return kitchenRepository.findById(id).orElseThrow(() -> new KitchenNotFoundException(id));
  }

  @Override
  public ArrayList<OrderDto> getAllOrders(UUID kitchenId) {
    ArrayList<OrderDto> orders = new ArrayList<>();

    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    List<UUID> orderIds = kitchen.getAllOrderIds();

    //uit order context data halen

    return orders;
  }

  @Override
  public OrderDto acceptOrder(UUID orderId, UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.acceptOrder(orderId);
    kitchenRepository.save(kitchen);
    OrderDto order = new OrderDto();
    return order;
  }

  @Override
  public void rejectOrder(UUID orderId, UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.removePendingOrder(orderId);
    kitchenRepository.save(kitchen);
//        return order;
  }

  @Override
  public void setStatus(UUID orderId, UUID kitchenId, OrderStatus status) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    if (kitchen.getOrdersInProcess().contains(orderId)) {
      //TODO: send status to Order context
    } else {
      throw new OrderNotFoundException(orderId);
    }
  }

  @Override
  public StockDtoOut getStock(UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    Storage storage = kitchen.getStock();
    StockDtoOut stockDtoOut = new StockDtoOut();
    stockDtoOut.kitchen = kitchenId;
    stockDtoOut.capacity = storage.getCapacity();
    List<ProductDto> ingredients = new ArrayList<>();
    for (ProductInStock ingredient : storage.getIngredientInStock()) {
      //dishIngredient dishIngredient = sender.getIngredient(ingredient.getId());
      ProductDto productDto = new ProductDto();
      productDto.amount = ingredient.getAmount();
      productDto.amountUnit = ingredient.getAmountUnit();
      //productDto.ingredientName = dishIngredient.getName;
      //productDto.allergies = dishIngredient.allergies;
      ingredients.add(productDto);
    }
    return stockDtoOut;
    //TODO: get ingredient names/allergies via dish
  }

  @Override
  public Kitchen createKitchen(KitchenDtoIn kitchenDtoIn) {
    if (kitchenRepository.findFirstByAddress(kitchenDtoIn.address).isPresent()) {
      throw new InvalidKitchenException("There is already a restaurant with the given address");
    }
    Storage storage = new Storage(kitchenDtoIn.capacity);
    Kitchen kitchen = new Kitchen(kitchenDtoIn.restaurantName, kitchenDtoIn.address, storage);

    return kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen updateKitchen(KitchenDtoIn kitchenDtoIn, UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    kitchen.setRestaurantName(kitchenDtoIn.restaurantName);
    kitchen.setAddress(kitchenDtoIn.address);
    kitchen.getStock().setCapacity(kitchenDtoIn.capacity);

    return kitchenRepository.save(kitchen);
  }

  @Override
  public void deleteKitchen(UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchenRepository.delete(kitchen);
  }

  @Override
  public MenuDto getMenu(UUID kitchenId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<UUID> menuIds = kitchen.getMenu();
    MenuDto menu = queueSender.getMenu(menuIds);

    return menu;
  }

  @Override
  public DishOutDto addDishToMenu(UUID kitchenId, DishInDto dto) {
    if (kitchenRepository.findById(kitchenId).isEmpty()) {
      throw new InvalidKitchenException("given kitchen does not exist");
    }

    return queueSender.addDish(kitchenId, dto);
  }

  @Override
  public ResponseEntity<IngredientOutDto> addProduct(UUID kitchenId, ProductDtoIn dto) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = dto.name;

    ResponseEntity<IngredientOutDto> p = proxy.createIngredient(ingredientInDto);
    ProductInStock product = new ProductInStock(p.getBody().id, dto.amount);

    return p;
  }

  @Override
  public ResponseEntity<IngredientOutDto> updateProduct(UUID kitchenId, UUID ingredientId,
      IngredientInDto productDto) {
    return null;
  }

  @Override
  public ResponseEntity<IngredientOutDto> deleteProduct(UUID kitchenId, UUID ingredientId) {
    return null;
  }
}
