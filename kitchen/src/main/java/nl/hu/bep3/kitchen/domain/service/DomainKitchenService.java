package nl.hu.bep3.kitchen.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.kitchen.application.request.KitchenDto;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.IngredientInStock;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.Stock;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import nl.hu.bep3.kitchen.domain.repository.KitchenRepository;
import nl.hu.bep3.kitchen.infrastructure.rabbitmq.QueueSender;
import org.springframework.stereotype.Service;

@Service
public class DomainKitchenService implements KitchenService{
    final KitchenRepository kitchenRepository;
    final QueueSender queueSender;

    public DomainKitchenService(KitchenRepository kitchenRepository, QueueSender queueSender) {
        this.kitchenRepository = kitchenRepository;
        this.queueSender = queueSender;
    }

    @Override
    public ArrayList<OrderDto> getAllOrders(UUID kitchenId) {
        ArrayList<OrderDto> orders = new ArrayList<>();

        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        List<UUID> orderIds = kitchen.getAllOrderIds();

        //uit order context data halen

        return orders;
    }

    @Override
    public OrderDto acceptOrder(UUID orderId, UUID kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        kitchen.acceptOrder(orderId);
        kitchenRepository.save(kitchen);
        OrderDto order = new OrderDto();
        return order;
    }

    @Override
    public void rejectOrder(UUID orderId, UUID kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        kitchen.removePendingOrder(orderId);
        kitchenRepository.save(kitchen);
//        return order;
    }

    @Override
    public void setStatus(UUID orderId, UUID kitchenId, OrderStatus status) {
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        if(kitchen.getOrdersInProcess().contains(orderId)) {
            //TODO: send status to Order context
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public StockDto getStock(UUID kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        Stock stock = kitchen.getStock();
        StockDto stockDto = new StockDto();
        stockDto.kitchen = kitchenId;
        stockDto.capacity = stock.getCapacity();
        List<IngredientDto> ingredients = new ArrayList<>();
        for (IngredientInStock ingredient: stock.getIngredientInStock()){
            //dishIngredient dishIngredient = sender.getIngredient(ingredient.getId());
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.amount = ingredient.getAmount();
            ingredientDto.amountUnit = ingredient.getAmountUnit();
            //ingredientDto.ingredientName = dishIngredient.getName;
            //ingredientDto.allergies = dishIngredient.allergies;
            ingredients.add(ingredientDto);
        }
        return stockDto;
        //TODO: get ingredient names/allergies via dish
    }

    @Override
    public Kitchen createKitchen(KitchenDto kitchenDto){
        if (kitchenRepository.findFirstByAddress(kitchenDto.address).isPresent()){
            throw new InvalidKitchenException("There is already a restaurant with the given address");
        }
        Stock stock = new Stock(kitchenDto.capacity);
        System.out.println(stock.getCapacity());
        Kitchen kitchen = new Kitchen(kitchenDto.restaurantName, kitchenDto.address, stock);
        return kitchenRepository.save(kitchen);
    }

    @Override
    public Kitchen updateKitchen(KitchenDto kitchenDto, UUID kitchenId) {
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        kitchen.setRestaurantName(kitchenDto.restaurantName);
        kitchen.setAddress(kitchenDto.address);
        kitchen.getStock().setCapacity(kitchenDto.capacity);
        return kitchenRepository.save(kitchen);
    }

    @Override
    public void getMenu(UUID kitchenId) {
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        //return queueSender.getMenu(kitchen.getMenu());
    }
}
