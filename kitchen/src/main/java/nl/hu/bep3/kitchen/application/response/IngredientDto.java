package nl.hu.bep3.kitchen.application.response;

import nl.hu.bep3.kitchen.domain.AmountUnit;
import nl.hu.bep3.kitchen.domain.FoodAllergy;

import java.util.ArrayList;
import java.util.List;

public class IngredientDto {
    public int amount;
    public String ingredientName;
    public List<FoodAllergy> allergies = new ArrayList<>();;
    public AmountUnit amountUnit;
}
