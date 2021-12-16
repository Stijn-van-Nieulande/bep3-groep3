package nl.hu.bep3.kitchen.application;

import nl.hu.bep3.kitchen.adapter.data.IngredientDto;
import nl.hu.bep3.kitchen.adapter.data.OrderDto;
import nl.hu.bep3.kitchen.adapter.data.StockDto;
import nl.hu.bep3.kitchen.adapter.repositories.KitchenRepository;
import nl.hu.bep3.kitchen.domain.IngredientInStock;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.Stock;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
import nl.hu.bep3.kitchen.domain.exceptions.orderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KitchenService {
    final KitchenRepository kitchenRepository;

    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public ArrayList<OrderDto> getAllOrders(Long kitchenId) {
        ArrayList<OrderDto> orders = new ArrayList<>();

        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        List<Long> orderIds = kitchen.getAllOrderIds();

        //uit order context data halen

        return orders;
    }

    public OrderDto acceptOrder(Long orderId, Long kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        kitchen.acceptOrder(orderId);
        kitchenRepository.save(kitchen);
        OrderDto order = new OrderDto();
        return order;
    }

    public void rejectOrder(Long orderId, Long kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        kitchen.removePendingOrder(orderId);
        kitchenRepository.save(kitchen);
//        return order;
    }

    public void setStatus(Long orderId, Long kitchenId, OrderStatus status) {
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        if(kitchen.getOrdersInProcess().contains(orderId)) {
            //TODO: send status to Order context
        } else {
            throw new orderNotFoundException(orderId);
        }
    }

    public StockDto getStock(Long kitchenId){
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
}
