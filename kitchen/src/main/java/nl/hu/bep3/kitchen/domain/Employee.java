package nl.hu.bep3.kitchen.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Employee {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    private String firstName;
    private String lastName;
    private double salaris;

    private EmployeeRole role;
}
