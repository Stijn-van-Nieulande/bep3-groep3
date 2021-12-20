package nl.hu.bep3.customer.application;

import nl.hu.bep3.customer.adapter.CustomerRepository;
import nl.hu.bep3.customer.adapter.dto.CustomerInputDTO;
import nl.hu.bep3.customer.adapter.dto.CustomerOutDTO;
import nl.hu.bep3.customer.application.exceptions.CustomerNotFoundException;
import nl.hu.bep3.customer.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerApplicationService {
    final CustomerRepository customerRepository;

    public CustomerApplicationService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerOutDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return mapCustomerToDTO(customer);
    }

    public CustomerOutDTO createCustomer(CustomerInputDTO customerInputDTO) {
        Customer customer = new Customer(customerInputDTO.firstName, customerInputDTO.lastName, customerInputDTO.address, customerInputDTO.email, customerInputDTO.phoneNumber);
        //TODO check duplicate :)
        Customer newCustomer = this.customerRepository.save(customer);
        return mapCustomerToDTO(newCustomer);
    }


    public CustomerOutDTO mapCustomerToDTO(Customer customer) {
        CustomerOutDTO customerOutDTO = new CustomerOutDTO();
        customerOutDTO.id = customer.getId();
        customerOutDTO.address = customer.getAddress();
        customerOutDTO.email = customer.getEmail();
        customerOutDTO.firstName = customer.getFirstName();
        customerOutDTO.lastName = customer.getLastName();
        customerOutDTO.phoneNumber = customer.getPhoneNumber();
        return customerOutDTO;
    }
}
