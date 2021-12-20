package nl.hu.bep3.customer.adapter;

import nl.hu.bep3.customer.adapter.dto.CustomerInputDTO;
import nl.hu.bep3.customer.adapter.dto.CustomerOutDTO;
import nl.hu.bep3.customer.application.CustomerApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerApplicationService service;

    public CustomerController(CustomerApplicationService service)
    {
        this.service = service;
    }

    @PostMapping()
    public Response createCustomer(CustomerInputDTO customerInputDTO){
        CustomerOutDTO customerOutDTO = service.createCustomer(customerInputDTO);
        return Response
                .status(Response.Status.OK)
                .entity(customerOutDTO)
                .build();
    }

    @GetMapping("/{id}")
    public Response getCustomerById(@RequestParam Long customerId){
        CustomerOutDTO customerOutDTO = service.getCustomerById(customerId);
        return Response
                .status(Response.Status.OK)
                .entity(customerOutDTO)
                .build();
    }
}
