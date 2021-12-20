package nl.hu.bep3.kitchen.application.response;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class StockDto {
    public ObjectId kitchen;
    public int capacity;
    public List<IngredientDto> ingredientList = new ArrayList<>();


}
