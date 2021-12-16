package nl.hu.bep3.kitchen.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IngredientInStock {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int amount;
    private Long ingredientId;
    private AmountUnit amountUnit;

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }
}
