package nl.hu.bep3.kitchen.domain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.IngredientAmount;
import nl.hu.bep3.kitchen.application.request.KitchenDtoIn;
import nl.hu.bep3.kitchen.application.request.ProductDtoIn;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.OrderResponseDto;
import nl.hu.bep3.kitchen.application.response.OrderResponseDto.DishOrder;
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
import nl.hu.bep3.kitchen.proxy.OrderServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DomainKitchenService implements KitchenService {

  final KitchenRepository kitchenRepository;
  final QueueSender queueSender;
  final DishServiceProxy dishProxy;
  final OrderServiceProxy orderProxy;

  public DomainKitchenService(KitchenRepository kitchenRepository, QueueSender queueSender,
      DishServiceProxy dishProxy, OrderServiceProxy orderProxy) {
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
  public ArrayList<OrderDto> getAllOrders(UUID kitchenId) {
    ArrayList<OrderDto> orders = new ArrayList<>();

    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    List<UUID> orderIds = kitchen.getAllOrderIds();

    //uit order context data halen

    return orders;
  }

  @Override
  public OrderResponseDto addOrder(OrderResponseDto order, UUID kitchenId){
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    kitchen.addPendingOrder(order.id);
    List<ProductInStock> ingredientsInStock = new ArrayList<>(kitchen.getStock().getIngredientInStock());

    HashMap<UUID, Float> ingredientAmounts = new HashMap<UUID, Float>();

    for (DishOrder dishOrder: order.dishOrders) {
      Dish dish = dishOrder.getDish();

      for (IngredientAmount ingredient : dish.getIngredients()){
        UUID ingredientId = ingredient.getIngredient().getId();
        if (ingredientsInStock.stream().anyMatch(
            stockIngredient -> stockIngredient.getId().equals(ingredient.getIngredient().getId()))){
          //er is een match dus hier toevoegen aan je nieuwe map
          if(ingredientAmounts.containsKey(ingredient.getIngredient().getId())){
            ingredientAmounts.put(
                ingredientId, ingredientAmounts.get(ingredientId) + (ingredient.getAmount() * dishOrder.getAmount()));
          }else{
            ingredientAmounts.put(
                ingredientId, ingredient.getAmount()*dishOrder.getAmount());
          }
        }else {
          //geen match dus order kan niet worden gemaakt.
          this.rejectOrder(order.id, kitchenId);
          return null;
        }
      }
    }

    //check to see if there is enough of the ingredient
    for (Map.Entry<UUID, Float> item: ingredientAmounts.entrySet()){
      ProductInStock productInStock = ingredientsInStock.stream().filter(
          stockProduct -> stockProduct.getId().equals(item.getKey())).findAny().orElse(null);
      if (productInStock == null){
        //MAJOR ERROR
        this.rejectOrder(order.id, kitchenId);
        return null;
      }
      if (item.getValue() > productInStock.getAmount()){
        this.rejectOrder(order.id, kitchenId);
        return null;
      }
    }
    this.acceptOrder(order.id, kitchenId);
    return order;
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
    List<IngredientOutDto> ingredients = new ArrayList<>();
    for (ProductInStock ingredient : storage.getIngredientInStock()) {
      ingredients.add(dishProxy.getIngredientById(ingredient.getId()).getBody());
//      ProductDto productDto = new ProductDto();
//      productDto.amount = ingredient.getAmount();
//      productDto.amountUnit = ingredient.getAmountUnit();
      //productDto.ingredientName = dishIngredient.getName;
      //productDto.allergies = dishIngredient.allergies;
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
  public Kitchen addProduct(UUID kitchenId, ProductDtoIn dto) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = dto.name;
    ingredientInDto.allergies = dto.allergies;

    ResponseEntity<IngredientOutDto> p = dishProxy.createIngredient(ingredientInDto);
    IngredientOutDto ingredient = p.getBody();
    if (ingredient == null){
      throw new NullPointerException("returned ingredient cannot be null");
    }
    ProductInStock product = new ProductInStock(ingredient.id, dto.amount, dto.amountUnit);

    kitchen.getStock().getIngredientInStock().add(product);

    return kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen updateProduct(UUID kitchenId, UUID ingredientId,
      ProductDtoIn productDto) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    IngredientInDto ingredientInDto = new IngredientInDto();
    ingredientInDto.name = productDto.name;
    ingredientInDto.allergies = productDto.allergies;

    List<ProductInStock> productsInStocks = kitchen.getStock().getIngredientInStock();
    for(ProductInStock product : productsInStocks){
      if (product.getId().equals(ingredientId)) {
        System.out.println("yes " + product);
        ResponseEntity<IngredientOutDto> p = dishProxy.updateIngredient(ingredientId, ingredientInDto);
        product.setAmount(productDto.amount);
      }
    }

    return kitchenRepository.save(kitchen);
  }

  @Override
  public Kitchen deleteProduct(UUID kitchenId, UUID ingredientId) {
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<ProductInStock> productsInStocks = kitchen.getStock().getIngredientInStock();
    for(ProductInStock product : productsInStocks){
      if (product.getId().equals(ingredientId)) {
        System.out.println("yes " + product);
        dishProxy.deleteIngredient(ingredientId);
        kitchen.getStock().getIngredientInStock().remove(product);
        break;
      }
    }

    return kitchenRepository.save(kitchen);
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
  public DishOutDto addDishToMenu(UUID kitchenId, DishInDto dishInDto){
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    DishOutDto response = dishProxy.createDish(dishInDto).getBody();
    kitchen.addToMenu(response.id);
    kitchenRepository.save(kitchen);
    return response;
  }

  @Override
  public MenuDto deleteDish(UUID kitchenId, UUID dishId){
    Kitchen kitchen = kitchenRepository.findById(kitchenId)
        .orElseThrow(() -> new KitchenNotFoundException(kitchenId));

    List<UUID> dishes = kitchen.getMenu();
    for(UUID dish : dishes){
      if (dish.equals(dishId)) {
        System.out.println("yes " + dish);
        dishProxy.deleteDish(dishId);
        kitchen.getMenu().remove(dishId);
        break;
      }
    }
    kitchenRepository.save(kitchen);

    return getMenu(kitchenId);
  }
}
