package nl.hu.bep3.kitchen.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Stock {
    //TODO: check if this works
    @Id
    private ObjectId id;

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
