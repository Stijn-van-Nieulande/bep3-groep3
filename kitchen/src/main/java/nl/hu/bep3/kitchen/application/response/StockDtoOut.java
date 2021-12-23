package nl.hu.bep3.kitchen.application.response;

import nl.hu.bep3.dish.application.response.IngredientOutDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockDtoOut {

  public UUID kitchen;
  public int capacity;
  public List<IngredientOutDto> ingredientList = new ArrayList<IngredientOutDto>();

}
