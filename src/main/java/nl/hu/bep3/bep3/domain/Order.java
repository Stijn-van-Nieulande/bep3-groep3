package nl.hu.bep3.bep3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Customer customer;
    private Date orderDate;
    private String adress;

    private Payment payment;
    private List<Dish> dishList = new ArrayList<>();
    private Status status;
    private Review review;
}
