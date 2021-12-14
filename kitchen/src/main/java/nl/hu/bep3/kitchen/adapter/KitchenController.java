package nl.hu.bep3.kitchen.adapter;

/*
Als keukenmedewerker/owner wil ik de opslag in kunnen zien
Als keukenmedewerker wil ik de status van een order kunnen updaten
Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren

(Als keukenmedewerker wil ik dat onmogelijke orders automatisch worden geweigerd)
*/

import nl.hu.bep3.kitchen.adapter.data.OrderDto;
import nl.hu.bep3.kitchen.application.KitchenService;
import nl.hu.bep3.kitchen.domain.OrderStatus;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Response;
import java.util.List;

@Controller
public class KitchenController {
    private final KitchenService service;

    public KitchenController(KitchenService service) {
        this.service = service;
    }

    //region Als keukenmedewerker wil ik inkomende orders kunnen inzien en accepteren/weigeren
    public Response getAllOrders(Long kitchenId) {
        List<OrderDto> orders = service.getAllOrders(kitchenId);

        return Response
                .status(Response.Status.OK)
                .entity(orders)
                .build();
    }

    public Response acceptOrder(Long orderId, Long kitchenId) {
        OrderDto order = service.acceptOrder(orderId, kitchenId);
        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }

    public Response rejectOrder(Long orderId, Long kitchenId) {
        Boolean deleted = service.rejectOrder(orderId, kitchenId);
        if (deleted) {
            return Response
                    .status(Response.Status.OK)
                    .entity("")
                    .build();
        }else{
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("")
                    .build();
        }
    }
    //endregion

    //region Als keukenmedewerker wil ik de status van een order kunnen updaten
    public Response setOrderStatus(Long orderId, OrderStatus status) {

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion

    //region Als keukenmedewerker/owner wil ik de opslag in kunnen zien
    public Response showStock() {

        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();
    }
    //endregion
}
