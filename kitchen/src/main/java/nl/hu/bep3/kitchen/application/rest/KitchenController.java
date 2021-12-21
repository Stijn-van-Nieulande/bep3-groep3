package nl.hu.bep3.kitchen.application.rest;

/*
Als keukenmedewerker/owner wil ik de opslag in kunnen zien
Als keukenmedewerker wil ik de status van een order kunnen updaten
Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren

(Als keukenmedewerker wil ik dat onmogelijke orders automatisch worden geweigerd)
*/

import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response;
import nl.hu.bep3.kitchen.application.request.KitchenDto;
import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {
    private final KitchenService service;

    public KitchenController(DomainKitchenService service) {
        this.service = service;
    }
    //TODO: get this shit out of here
  /*  @GetMapping()
        public String bruAddKitchen(){
        return service.addKitchen();
    }*/

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Kitchen> createKitchen(@RequestBody KitchenDto kitchenDto) {
        try {
            return new ResponseEntity(service.createKitchen(kitchenDto), HttpStatus.OK);
        }catch (InvalidKitchenException | NullPointerException exception ){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{id}",  consumes = "application/json")
    public Kitchen updateKitchen(@PathVariable("id") UUID kitchenId, @RequestBody KitchenDto kitchenDto) {
        return service.updateKitchen(kitchenDto, kitchenId);
    }

    //region Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren
    public Response getAllOrders(UUID kitchenId) {
        List<OrderDto> orders = service.getAllOrders(kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(orders)
                .build();
    }

    public Response acceptOrder(UUID orderId, UUID kitchenId) {
        OrderDto order = service.acceptOrder(orderId, kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(order)
                .build();
    }

    public Response rejectOrder(UUID orderId, UUID kitchenId) {
        service.rejectOrder(orderId, kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion

    //region Als keukenmedewerker wil ik de status van een order kunnen updaten
    public Response setOrderStatus(UUID orderId, UUID kitchenId, OrderStatus status) {
        service.setStatus(orderId, kitchenId, status);

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion

    //region Als keukenmedewerker/owner wil ik de opslag in kunnen zien
    @GetMapping("/stock/{id}")
    public Response showStock(@PathVariable("id") UUID kitchenId) {
        StockDto stockDto = service.getStock(kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(stockDto)
                .build();
    }

    @PatchMapping("/stock/{id}")
    public Response addToStock(@PathVariable("id") UUID kitchenId, @RequestBody IngredientDto ingredientDto){


        return Response
                .status(Response.Status.OK)
                .entity("hurb")
                .build();
    }
    //endregion

    //region Als owner wil ik het menu van een restaurant aan kunnen passen.
    @GetMapping("/menu/{id}")
    public OrderDto getMenu(@PathVariable("id") UUID kitchenId) {
        service.getMenu(kitchenId);

        //TODO: actually give menu
        OrderDto dto = new OrderDto();
        dto.id = UUID.randomUUID();
        dto.capacity = 420;

        return dto;
    }

    @DeleteMapping("/menu/{kitchenId}/{DishId}")
    public Response getMenu(@PathVariable("kitchenId") UUID kitchenId, @PathVariable("DishId") UUID DishId) {

        System.out.println(kitchenId + " - " + DishId);
        return Response
                .status(Response.Status.OK)
                .entity("hurb")
                .build();
    }

    //endregion
}
