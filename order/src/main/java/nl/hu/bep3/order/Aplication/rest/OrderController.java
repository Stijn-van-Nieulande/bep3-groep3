package nl.hu.bep3.order.Aplication.rest;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.domain.service.DomainOrderService;
import nl.hu.bep3.order.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final DomainOrderService orderService;

    public OrderController(DomainOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/makeOrder")
    public Order placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return this.orderService.placeNewOrder(orderRequestDTO);
    }

    @PutMapping("/{id}")
    public boolean setStatus(@PathVariable Long id, @RequestBody String status) {
        return this.orderService.setStatus(id, status);
    }

    @PutMapping("/{id}")
    public ReviewRequestDTO setReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) { //kan dit? dto meegeven meteen?
        return this.orderService.setReview(id, reviewRequestDTO);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteOrder(@PathVariable Long id) { //kan dit? dto meegeven meteen?
        return this.orderService.deleteOrder(id);
        return HttpStatus.OK;
    }

//    @PutMapping("/{id}")
//    public boolean setReview(@PathVariable Long id, @RequestBody int rating, @RequestBody String message) { //kan dit? dto meegeven meteen?
//        return this.service.setReview(id, rating, message);
//    }

}
