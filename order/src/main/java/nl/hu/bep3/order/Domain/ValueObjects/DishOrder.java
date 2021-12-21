package nl.hu.bep3.order.domain.ValueObjects;

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
