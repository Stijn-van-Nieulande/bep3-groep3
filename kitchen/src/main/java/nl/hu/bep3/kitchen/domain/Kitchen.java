package nl.hu.bep3.kitchen.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Kitchen {
    @Id
    private UUID id;

    private String restaurantName;
    private String address;

//    @OneToOne
    private Stock stock;

//    private List<Employee> employees;

//    @ElementCollection
//    @CollectionTable(name = "kitchen_menu")
    private List<UUID> menu = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "pending_orders")
    private List<UUID> pendingOrders = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "orders")
    private List<UUID> ordersInProcess = new ArrayList<>();

    public Kitchen(){ }

    public Kitchen(String restaurantName, String address, Stock stock){
        Objects.requireNonNull(restaurantName, "Restaurant name cannot be null!");
        Objects.requireNonNull(address, "Address cannot be null!");
        Objects.requireNonNull(stock, "stock cannot be null!");

        this.restaurantName = restaurantName;
        this.address = address;
        this.stock = stock;
    }

    public List<UUID> getAllOrderIds() {
        List<UUID> orders = new ArrayList<>();

        orders.addAll(pendingOrders);
        orders.addAll(ordersInProcess);

        return orders;
    }

    public void acceptOrder(UUID pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
            this.ordersInProcess.add(pendingOrder);
        } else {
            throw new OrderNotFoundException(pendingOrder);
        }
    }

    public void removePendingOrder(UUID pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
        }
        throw new OrderNotFoundException(pendingOrder);
    }

    public Stock getStock() {
        return stock;
    }

    public List<UUID> getOrdersInProcess() {
        return ordersInProcess;
    }

    public List<UUID> getMenu() {
        return menu;
    }

    public Boolean addToMenu(UUID dish) {
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
