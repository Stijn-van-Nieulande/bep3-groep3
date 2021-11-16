package nl.hu.bep3.bep3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dish
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @Column
    private double price;

    @Transient
    private List<Ingredient> ingredients = new ArrayList<>();
}
