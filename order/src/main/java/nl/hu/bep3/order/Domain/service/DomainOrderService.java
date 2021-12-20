package nl.hu.bep3.order.Domain.service;

import nl.hu.bep3.order.infrastructure.repository.mango.SpringDataMongoOrderRepository;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.Domain.Order;
import nl.hu.bep3.order.Domain.exeption.OrderNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DomainOrderService {
    private SpringDataMongoOrderRepository springDataMongoOrderRepository;
    private Order order;

    public DomainOrderService(SpringDataMongoOrderRepository springDataMongoOrderRepository) {
        this.springDataMongoOrderRepository = springDataMongoOrderRepository;
    }

    public Order placeNewOrder(){
        Order order = new Order();
        return order;
    }

    public boolean setStatus(Long id, String status){
        try {
            order = getOrderById(id);
            order.setStatus(status);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Order getOrderById(Long orderId) {
        return this.springDataMongoOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound(orderId));
    }

    public ReviewRequestDTO setReview(Long id, ReviewRequestDTO reviewRequestDTO){
        order = getOrderById(id);
        return order.setReview(reviewRequestDTO.message, reviewRequestDTO.rating);
    }
}
