package nl.hu.bep3.customer.adapter.dto;

import org.springframework.data.mongodb.core.mapping.MongoId;

public class CustomerOutDTO {
    public MongoId id;
    public String firstName;
    public String lastName;
    public String address;
    public String email;
    public String phoneNumber;
}
