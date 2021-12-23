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
import nl.hu.bep3.order.application.response.OrderResponseDto;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDto;
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
      final KitchenRepository kitchenRepository,
      final QueueSender queueSender,
      final DishServiceProxy dishProxy,
      final OrderServiceProxy orderProxy) {
    this.kitchenRepository = kitchenRepository;
    this.queueSender = queueSender;
    this.dishProxy = dishProxy;
    this.orderProxy = orderProxy;
  }

  @Override
  public List<Kitchen> findAll() {
    return this.kitchenRepository.findAll();
  }

  public Kitchen findById(final UUID id) {
    return this.kitchenRepository.findById(id).orElseThrow(() -> new KitchenNotFoundException(id));
  }

  @Override
  public List<OrderResponseDto> getAllOrders(final UUID kitchenId) {
    final ArrayList<OrderResponseDto> orders = new ArrayList<>();

    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    final List<UUID> orderIds = kitchen.getAllOrderIds();

    for (final UUID orderId : orderIds) {
      orders.add(this.orderProxy.getOrderById(orderId));
    }
    return orders;
    // uit order context data halen
  }

  @Override
  public OrderDto addOrder(final OrderResponseToKitchenDto order, final UUID kitchenId) {
    Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.addPendingOrder(order.id);

    kitchen = this.kitchenRepository.save(kitchen);

    final List<ProductInStock> ingredientsInStock =
        new ArrayList<>(kitchen.getStorage().getIngredientInStock());

    final HashMap<UUID, Double> ingredientAmounts = new HashMap<UUID, Double>();

    for (final DishOrder dishOrder : order.dishOrders) {
      final DishOutDto dish = this.dishProxy.getDishById(dishOrder.getDish()).getBody();

      // FIXME: dish can be null
      for (final IngredientAmountOutDto ingredient : dish.ingredients) {
        final UUID ingredientId = ingredient.id;
        // FIXME: getId can be null
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
    for (final Map.Entry<UUID, Double> item : ingredientAmounts.entrySet()) {
      // FIXME: getId can be null
      final ProductInStock productInStock =
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

    this.kitchenRepository.save(kitchen);
    return this.acceptOrder(order.id, kitchenId);
  }

  @Override
  public OrderDto acceptOrder(final UUID orderId, final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.acceptOrder(orderId);

    System.out.println(kitchen.getOrdersInProcess());

    this.kitchenRepository.save(kitchen);
    final OrderDto order = new OrderDto(orderId, "accepted");

    this.orderProxy.setStatus(orderId, "accepted");
    return order;
  }

  @Override
  public OrderDto rejectOrder(final UUID orderId, final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.removePendingOrder(orderId);
    System.out.println(
        "DEBUG 1: "
            + kitchen.getOrdersInProcess().stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", ")));

    this.kitchenRepository.save(kitchen);
    final OrderDto order = new OrderDto(orderId, "cancelled");

    this.orderProxy.setStatus(orderId, "cancelled");
    return order;
  }

  @Override
  public OrderDto setStatus(final UUID orderId, final UUID kitchenId, final String status) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    if (kitchen.getOrdersInProcess().contains(orderId)) {
      this.orderProxy.setStatus(orderId, status);
      return new OrderDto(orderId, status);
    } else {
      throw new OrderNotFoundException(orderId);
    }
  }

  @Override
  public StockDtoOut getStock(final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    final Storage storage = kitchen.getStorage();
    final StockDtoOut stockDtoOut = new StockDtoOut();
    stockDtoOut.kitchen = kitchenId;
    stockDtoOut.capacity = storage.getCapacity();
    final List<IngredientOutDto> ingredients = new ArrayList<>();
    for (final ProductInStock ingredient : storage.getIngredientInStock()) {
      ingredients.add(this.dishProxy.getIngredientById(ingredient.getId()).getBody());
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
  public Kitchen createKitchen(final KitchenDtoIn kitchenDtoIn) {
    if (this.kitchenRepository.findFirstByAddress(kitchenDtoIn.address).isPresent()) {
      throw new InvalidKitchenException("There is already a restaurant with the given address");
    }
    final Storage storage = new Storage(kitchenDtoIn.capacity);
    final Kitchen kitchen = new Kitchen(kitchenDtoIn.restaurantName, kitchenDtoIn.address, storage);

    return this.kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen updateKitchen(final KitchenDtoIn kitchenDtoIn, final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    kitchen.setRestaurantName(kitchenDtoIn.restaurantName);
    kitchen.setAddress(kitchenDtoIn.address);
    kitchen.getStorage().setCapacity(kitchenDtoIn.capacity);

    return this.kitchenRepository.save(kitchen);
  }

  @Override
  public void deleteKitchen(final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    this.kitchenRepository.delete(kitchen);
  }

  @Override
  public Kitchen addProduct(final UUID kitchenId, final ProductDtoIn dto) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    final IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = dto.name;
    ingredientInDto.allergies = dto.allergies;

    final ResponseEntity<IngredientOutDto> p = this.dishProxy.createIngredient(ingredientInDto);
    final IngredientOutDto ingredient = p.getBody();
    if (ingredient == null) {
      throw new NullPointerException("returned ingredient cannot be null");
    }
    final ProductInStock product = new ProductInStock(ingredient.id, dto.amount, dto.amountUnit);

    kitchen.getStorage().getIngredientInStock().add(product);

    return this.kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen updateProduct(
      final UUID kitchenId, final UUID ingredientId, final ProductDtoIn productDto) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    final IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = productDto.name;
    ingredientInDto.allergies = productDto.allergies;

    final List<ProductInStock> productsInStocks = kitchen.getStorage().getIngredientInStock();
    for (final ProductInStock product : productsInStocks) {
      // FIXME: getId can be null
      if (product.getId().equals(ingredientId)) {
        final ResponseEntity<IngredientOutDto> p =
            this.dishProxy.updateIngredient(ingredientId, ingredientInDto);
        product.setAmount(productDto.amount);
      }
    }

    return this.kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen deleteProduct(final UUID kitchenId, final UUID ingredientId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    final List<ProductInStock> productsInStocks = kitchen.getStorage().getIngredientInStock();
    for (final ProductInStock product : productsInStocks) {
      assert product.getId() != null;
      if (product.getId().equals(ingredientId)) {
        this.dishProxy.deleteIngredient(ingredientId);
        kitchen.getStorage().getIngredientInStock().remove(product);
        break;
      }
    }

    return this.kitchenRepository.save(kitchen);
  }

  @Override
  public MenuDto getMenu(final UUID kitchenId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    final List<UUID> menuIds = kitchen.getMenu();

    return this.queueSender.getMenu(menuIds);
  }

  @Override
  public DishOutDto addDishToMenu(final UUID kitchenId, final DishInDto dishInDto) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    final DishOutDto response = this.dishProxy.createDish(dishInDto).getBody();
    // FIXME: response can be null
    kitchen.addToMenu(response.id);
    this.kitchenRepository.save(kitchen);
    return response;
  }

  @Override
  public MenuDto deleteDish(final UUID kitchenId, final UUID dishId) {
    final Kitchen kitchen =
        this.kitchenRepository
            .findById(kitchenId)
            .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    final List<UUID> dishes = kitchen.getMenu();
    for (final UUID dish : dishes) {
      if (dish.equals(dishId)) {
        this.dishProxy.deleteDish(dishId);
        kitchen.getMenu().remove(dishId);
        break;
      }
    }
    this.kitchenRepository.save(kitchen);

    return this.getMenu(kitchenId);
  }


}
