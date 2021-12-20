package nl.hu.bep3.order.Aplication.rest;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.Domain.service.DomainOrderService;
import nl.hu.bep3.order.Domain.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final DomainOrderService service;

    public OrderController(DomainOrderService service) {
        this.service = service;
    }

    @PostMapping("/makeOrder")
    public Order placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return this.service.placeNewOrder();
    }

    @PutMapping("/{id}")
    public boolean setStatus(@PathVariable Long id, @RequestBody String status) {
        return this.service.setStatus(id, status);
    }

    @PutMapping("/{id}")
    public ReviewRequestDTO setReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) { //kan dit? dto meegeven meteen?
        return this.service.setReview(id, reviewRequestDTO);
    }

//    @PutMapping("/{id}")
//    public boolean setReview(@PathVariable Long id, @RequestBody int rating, @RequestBody String message) { //kan dit? dto meegeven meteen?
//        return this.service.setReview(id, rating, message);
//    }

}
