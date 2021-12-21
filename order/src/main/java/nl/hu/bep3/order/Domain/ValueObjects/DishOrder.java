package nl.hu.bep3.order.domain.valueobjects;

import nl.hu.bep3.dish.domain.Dish;

public class DishOrder {
    private Long prodictId;
    private int amount;
    private Dish dish;

    public DishOrder(int amount, Dish dish) {
        this.amount = amount;
        this.dish = dish;
    }

    public float calcPriceDishOrder(){
        return dish.getPrice * amount;

    }
}
