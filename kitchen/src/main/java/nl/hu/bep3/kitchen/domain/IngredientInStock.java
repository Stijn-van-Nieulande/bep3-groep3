package nl.hu.bep3.kitchen.domain;

import nl.hu.bep3.dish.domain.AmountUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document
public class IngredientInStock {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int amount;
    private UUID ingredientId;
    private AmountUnit amountUnit;

    public UUID getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }
}
