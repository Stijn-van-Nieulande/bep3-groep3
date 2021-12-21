package nl.hu.bep3.kitchen.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stock {
    //TODO: check if this works
    @Id
    private UUID id;

    private Kitchen kitchen;

    private int capacity;

    private List<IngredientInStock> ingredientInStock;

    public Stock(){}

    public Stock(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<IngredientInStock> getIngredientInStock() {
        return ingredientInStock;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
