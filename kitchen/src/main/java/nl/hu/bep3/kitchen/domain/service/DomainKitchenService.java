package nl.hu.bep3.kitchen.domain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientAmountOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDtoOut;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.ProductInStock;
import nl.hu.bep3.kitchen.domain.Storage;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import nl.hu.bep3.kitchen.domain.repository.KitchenRepository;
import nl.hu.bep3.kitchen.infrastructure.rabbitmq.QueueSender;
import nl.hu.bep3.kitchen.proxy.DishServiceProxy;
import nl.hu.bep3.kitchen.proxy.OrderServiceProxy;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDTO;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DomainKitchenService implements KitchenService {

  final KitchenRepository kitchenRepository;
  final QueueSender queueSender;
  final DishServiceProxy dishProxy;
  final OrderServiceProxy orderProxy;

  public DomainKitchenService(
      KitchenRepository kitchenRepository,
      QueueSender queueSender,
      DishServiceProxy dishProxy,
      OrderServiceProxy orderProxy) {
    this.kitchenRepository = kitchenRepository;
    this.queueSender = queueSender;
    this.dishProxy = dishProxy;
    this.orderProxy = orderProxy;
  }

  @Override
  public List<Kitchen> findAll() {
    return kitchenRepository.findAll();
  }

  public Kitchen findById(UUID id) {
    return kitchenRepository.findById(id).orElseThrow(() -> new KitchenNotFoundException(id));
  }

  @Override
  public List<OrderResponseDTO> getAllOrders(UUID kitchenId) {
    ArrayList<OrderResponseDTO> orders = new ArrayList<>();

    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    List<UUID> orderIds = kitchen.getAllOrderIds();
    
    for (UUID orderId : orderIds) {
        orders.add(orderProxy.getOrderById(orderId));
    }
    return orders;
    // uit order context data halen
  }

  @Override
  public OrderDto addOrder(OrderResponseToKitchenDTO order, UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.addPendingOrder(order.id);

    kitchen = kitchenRepository.save(kitchen);

    List<ProductInStock> ingredientsInStock =
        new ArrayList<>(kitchen.getStorage().getIngredientInStock());

    HashMap<UUID, Double> ingredientAmounts = new HashMap<UUID, Double>();

    for (DishOrder dishOrder : order.dishOrders) {
      DishOutDto dish = dishProxy.getDishById(dishOrder.getDish()).getBody();

      for (IngredientAmountOutDto ingredient : dish.ingredients) {
        UUID ingredientId = ingredient.id;
        if (ingredientsInStock.stream()
            .anyMatch(stockIngredient -> stockIngredient.getId().equals(ingredient.id))) {
          // er is een match dus hier toevoegen aan je nieuwe map
          if (ingredientAmounts.containsKey(ingredient.id)) {
            ingredientAmounts.put(
                ingredientId,
                ingredientAmounts.get(ingredientId) + (ingredient.amount * dishOrder.getAmount()));
          } else {
            ingredientAmounts.put(ingredientId, ingredient.amount * dishOrder.getAmount());
          }
        } else {
          System.out.println("Break 1");
          // geen match dus order kan niet worden gemaakt.
          return this.rejectOrder(order.id, kitchenId);
        }
      }
    }

    // check to see if there is enough of the ingredient
    for (Map.Entry<UUID, Double> item : ingredientAmounts.entrySet()) {
      ProductInStock productInStock =
          ingredientsInStock.stream()
              .filter(stockProduct -> stockProduct.getId().equals(item.getKey()))
              .findAny()
              .orElse(null);
      if (productInStock == null) {
        // MAJOR ERROR
        System.out.println("Break 2");
        return this.rejectOrder(order.id, kitchenId);
      }
      if (item.getValue() > productInStock.getAmount()) {
        System.out.println("Break 3");
        return this.rejectOrder(order.id, kitchenId);
      }
    }

    kitchenRepository.save(kitchen);
    return this.acceptOrder(order.id, kitchenId);
  }

  @Override
  public OrderDto acceptOrder(UUID orderId, UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.acceptOrder(orderId);

    System.out.println(kitchen.getOrdersInProcess());

    kitchenRepository.save(kitchen);
    OrderDto order = new OrderDto(orderId, "accepted");

    orderProxy.setStatus(orderId, "accepted");
    return order;
  }

  @Override
  public OrderDto rejectOrder(UUID orderId, UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.removePendingOrder(orderId);
    System.out.println(
        "DEBUG 1: "
            + kitchen.getOrdersInProcess().stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", ")));


    kitchenRepository.save(kitchen);
    OrderDto order = new OrderDto(orderId, "cancelled");

    orderProxy.setStatus(orderId, "cancelled");
    return order;
  }

  @Override
  public OrderDto setStatus(UUID orderId, UUID kitchenId, String status) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    if (kitchen.getOrdersInProcess().contains(orderId)) {
      orderProxy.setStatus(orderId, status);
      return new OrderDto(orderId, status);
    } else {
      throw new OrderNotFoundException(orderId);
    }
  }

  @Override
  public StockDtoOut getStock(UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    Storage storage = kitchen.getStorage();
    StockDtoOut stockDtoOut = new StockDtoOut();
    stockDtoOut.kitchen = kitchenId;
    stockDtoOut.capacity = storage.getCapacity();
    List<IngredientOutDto> ingredients = new ArrayList<>();
    for (ProductInStock ingredient : storage.getIngredientInStock()) {
      ingredients.add(dishProxy.getIngredientById(ingredient.getId()).getBody());
      //      ProductDto productDto = new ProductDto();
      //      productDto.amount = ingredient.getAmount();
      //      productDto.amountUnit = ingredient.getAmountUnit();
      // productDto.ingredientName = dishIngredient.getName;
      // productDto.allergies = dishIngredient.allergies;
      //      ingredients.add(productDto);
    }
    stockDtoOut.ingredientList = ingredients;
    return stockDtoOut;
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
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));


    kitchen.setRestaurantName(kitchenDtoIn.restaurantName);
    kitchen.setAddress(kitchenDtoIn.address);
    kitchen.getStorage().setCapacity(kitchenDtoIn.capacity);

    return kitchenRepository.save(kitchen);
  }

  @Override
  public void deleteKitchen(UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchenRepository.delete(kitchen);
  }

  @Override
  public Kitchen addProduct(UUID kitchenId, ProductDtoIn dto) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = dto.name;
    ingredientInDto.allergies = dto.allergies;

    ResponseEntity<IngredientOutDto> p = dishProxy.createIngredient(ingredientInDto);
    IngredientOutDto ingredient = p.getBody();
    if (ingredient == null) {
      throw new NullPointerException("returned ingredient cannot be null");
    }
    ProductInStock product = new ProductInStock(ingredient.id, dto.amount, dto.amountUnit);

    kitchen.getStorage().getIngredientInStock().add(product);

    return kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen updateProduct(UUID kitchenId, UUID ingredientId, ProductDtoIn productDto) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = productDto.name;
    ingredientInDto.allergies = productDto.allergies;

    List<ProductInStock> productsInStocks = kitchen.getStorage().getIngredientInStock();
    for (ProductInStock product : productsInStocks) {
      if (product.getId().equals(ingredientId)) {
        ResponseEntity<IngredientOutDto> p =
            dishProxy.updateIngredient(ingredientId, ingredientInDto);
        product.setAmount(productDto.amount);
      }
    }

    return kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen deleteProduct(UUID kitchenId, UUID ingredientId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<ProductInStock> productsInStocks = kitchen.getStorage().getIngredientInStock();
    for (ProductInStock product : productsInStocks) {
      if (product.getId().equals(ingredientId)) {
        dishProxy.deleteIngredient(ingredientId);
        kitchen.getStorage().getIngredientInStock().remove(product);
        break;
      }
    }

    return kitchenRepository.save(kitchen);
  }

  @Override
  public MenuDto getMenu(UUID kitchenId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<UUID> menuIds = kitchen.getMenu();
    MenuDto menu = queueSender.getMenu(menuIds);

    return menu;
  }

  @Override
  public DishOutDto addDishToMenu(UUID kitchenId, DishInDto dishInDto) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    DishOutDto response = dishProxy.createDish(dishInDto).getBody();
    kitchen.addToMenu(response.id);
    kitchenRepository.save(kitchen);
    return response;
  }

  @Override
  public MenuDto deleteDish(UUID kitchenId, UUID dishId) {
    Kitchen kitchen =
        kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<UUID> dishes = kitchen.getMenu();
    for (UUID dish : dishes) {
      if (dish.equals(dishId)) {
        dishProxy.deleteDish(dishId);
        kitchen.getMenu().remove(dishId);
        break;
      }
    }
    kitchenRepository.save(kitchen);

    return getMenu(kitchenId);
  }
}
