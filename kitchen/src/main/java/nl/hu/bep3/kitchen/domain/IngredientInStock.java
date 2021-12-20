package nl.hu.bep3.kitchen.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IngredientInStock {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    private int amount;
    private ObjectId ingredientId;
    private AmountUnit amountUnit;

    public ObjectId getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public ObjectId getIngredientId() {
        return ingredientId;
    }

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }
}
