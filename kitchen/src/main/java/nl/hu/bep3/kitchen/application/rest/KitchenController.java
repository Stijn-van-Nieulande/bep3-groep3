package nl.hu.bep3.kitchen.application.rest;

/*
Als keukenmedewerker/owner wil ik de opslag in kunnen zien
Als keukenmedewerker wil ik de status van een order kunnen updaten
Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren

(Als keukenmedewerker wil ik dat onmogelijke orders automatisch worden geweigerd)
*/

import nl.hu.bep3.kitchen.application.response.IngredientDto;
import nl.hu.bep3.kitchen.application.request.KitchenDto;
import nl.hu.bep3.kitchen.application.response.OrderDto;
import nl.hu.bep3.kitchen.application.response.StockDto;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.Kitchen;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import nl.hu.bep3.kitchen.domain.exceptions.InvalidKitchenException;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

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
    public Kitchen updateKitchen(@PathVariable("id") ObjectId kitchenId, @RequestBody KitchenDto kitchenDto) {
        return service.updateKitchen(kitchenDto, kitchenId);
    }

    //region Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren
    public Response getAllOrders(ObjectId kitchenId) {
        List<OrderDto> orders = service.getAllOrders(kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(orders)
                .build();
    }

    public Response acceptOrder(ObjectId orderId, ObjectId kitchenId) {
        OrderDto order = service.acceptOrder(orderId, kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(order)
                .build();
    }

    public Response rejectOrder(ObjectId orderId, ObjectId kitchenId) {
        service.rejectOrder(orderId, kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion

    //region Als keukenmedewerker wil ik de status van een order kunnen updaten
    public Response setOrderStatus(ObjectId orderId, ObjectId kitchenId, OrderStatus status) {
        service.setStatus(orderId, kitchenId, status);

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion

    //region Als keukenmedewerker/owner wil ik de opslag in kunnen zien
    @GetMapping("/stock/{id}")
    public Response showStock(@PathVariable("id") ObjectId kitchenId) {
        StockDto stockDto = service.getStock(kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(stockDto)
                .build();
    }

    @PatchMapping("/stock/{id}")
    public Response addToStock(@PathVariable("id") ObjectId kitchenId, @RequestBody IngredientDto ingredientDto){


        return Response
                .status(Response.Status.OK)
                .entity("hurb")
                .build();
    }
    //endregion

    //region Als owner wil ik het menu van een restaurant aan kunnen passen.
    @GetMapping("/menu/{id}")
    public OrderDto getMenu(@PathVariable("id") ObjectId kitchenId) {
        service.getMenu(kitchenId);

        OrderDto dto = new OrderDto();
        dto.id = new ObjectId("4f693d40e4b04cde19f17205");
        dto.capacity = 420;

        return dto;
    }

    @DeleteMapping("/menu/{kitchenId}/{DishId}")
    public Response getMenu(@PathVariable("kitchenId") ObjectId kitchenId, @PathVariable("DishId") ObjectId DishId) {

        System.out.println(kitchenId + " - " + DishId);
        return Response
                .status(Response.Status.OK)
                .entity("hurb")
                .build();
    }

    //endregion
}
