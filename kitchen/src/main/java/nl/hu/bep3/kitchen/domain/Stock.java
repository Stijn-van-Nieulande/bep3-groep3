package nl.hu.bep3.kitchen.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Stock {
    //TODO: check if this works
    @Id
//    @Column(name = "child_id", unique = true, nullable = false)
    private Long id;

//    @MapsId
//    @OneToOne
//    @JoinColumn(name = "child_id")
    private Kitchen kitchen;

    private int capacity;

//    @OneToMany
    private List<IngredientInStock> ingredientInStock;

    public Stock(){}

    public int getCapacity() {
        return capacity;
    }

    public List<IngredientInStock> getIngredientInStock() {
        return ingredientInStock;
    }


}
