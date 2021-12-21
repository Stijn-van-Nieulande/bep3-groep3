package nl.hu.bep3.kitchen.application.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockDto {
    public UUID kitchen;
    public int capacity;
    public List<IngredientDto> ingredientList = new ArrayList<>();


}
