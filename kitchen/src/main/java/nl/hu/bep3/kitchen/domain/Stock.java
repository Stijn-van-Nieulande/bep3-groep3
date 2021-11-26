package nl.hu.bep3.kitchen.domain;

import nl.hu.bep3.kitchen.domain.idObjects.StockId;

import java.util.List;

public class Stock {
    private StockId id;

    private int capacity;
    private List<IngredientInStock> ingredientInStock;
}
