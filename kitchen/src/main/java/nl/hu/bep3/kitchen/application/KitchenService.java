package nl.hu.bep3.kitchen.application;

import nl.hu.bep3.kitchen.adapter.data.OrderDto;
import nl.hu.bep3.kitchen.adapter.repositories.KitchenRepository;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.exceptions.KitchenNotFoundException;
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
        kitchen.addPendingOrder(orderId);
        kitchenRepository.save(kitchen);
        OrderDto order = new OrderDto();
        return order;
    }

    public Boolean rejectOrder(Long orderId, Long kitchenId){
        Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
        Boolean reject = kitchen.removePendingOrder(orderId);
        kitchenRepository.save(kitchen);
        return reject;
    }
}
