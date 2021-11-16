package nl.hu.bep3.bep3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Customer reviewer;
    private Order order;
    private String review;
    private Integer rating;
}
