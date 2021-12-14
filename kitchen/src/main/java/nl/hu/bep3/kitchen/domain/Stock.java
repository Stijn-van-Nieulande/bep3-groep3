package nl.hu.bep3.kitchen.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stock {
    //TODO: check if this works
    @Id
    @Column(name = "child_id", unique = true, nullable = false)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "child_id")
    private Kitchen kitchen;

    private int capacity;

    @OneToMany
    private List<IngredientInStock> ingredientInStock;
}
