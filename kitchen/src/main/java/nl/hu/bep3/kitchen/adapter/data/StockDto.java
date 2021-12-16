package nl.hu.bep3.kitchen.adapter.data;

import java.util.ArrayList;
import java.util.List;

public class StockDto {
    public Long kitchen;
    public int capacity;
    public List<IngredientDto> ingredientList = new ArrayList<>();
}
