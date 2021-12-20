package nl.hu.bep3.kitchen.domain;

import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Kitchen {
    @Id
    private ObjectId id;

    private String restaurantName;
    private String address;

//    @OneToOne
    private Stock stock;

//    private List<Employee> employees;

//    @ElementCollection
//    @CollectionTable(name = "kitchen_menu")
    private List<ObjectId> menu = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "pending_orders")
    private List<ObjectId> pendingOrders = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "orders")
    private List<ObjectId> ordersInProcess = new ArrayList<>();

    public Kitchen(){ }

    public Kitchen(String restaurantName, String address, Stock stock){
        Objects.requireNonNull(restaurantName, "Restaurant name cannot be null!");
        Objects.requireNonNull(address, "Address cannot be null!");
        Objects.requireNonNull(stock, "stock cannot be null!");

        this.restaurantName = restaurantName;
        this.address = address;
        this.stock = stock;
    }

    public List<ObjectId> getAllOrderIds() {
        List<ObjectId> orders = new ArrayList<>();

        orders.addAll(pendingOrders);
        orders.addAll(ordersInProcess);

        return orders;
    }

    public void acceptOrder(ObjectId pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
            this.ordersInProcess.add(pendingOrder);
        } else {
            throw new OrderNotFoundException(pendingOrder);
        }
    }

    public void removePendingOrder(ObjectId pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
        }
        throw new OrderNotFoundException(pendingOrder);
    }

    public Stock getStock() {
        return stock;
    }

    public List<ObjectId> getOrdersInProcess() {
        return ordersInProcess;
    }

    public List<ObjectId> getMenu() {
        return menu;
    }

    public Boolean addToMenu(ObjectId dish) {
        if (!menu.contains(dish)) {
            return this.menu.add(dish);
        }
        return false;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
