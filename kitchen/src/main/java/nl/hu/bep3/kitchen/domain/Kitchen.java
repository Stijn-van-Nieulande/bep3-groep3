package nl.hu.bep3.kitchen.domain;

import nl.hu.bep3.kitchen.domain.idObjects.DishId;
import nl.hu.bep3.kitchen.domain.idObjects.EmployeeId;
import nl.hu.bep3.kitchen.domain.idObjects.KitchenId;
import nl.hu.bep3.kitchen.domain.idObjects.OrderId;

import java.util.List;

public class Kitchen {
    private String restaurantName;
    private String adress;
    private KitchenId id;

    private List<EmployeeId> employees;
    private List<DishId> dishes;
    private List<OrderId> orders;
}
