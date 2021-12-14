package nl.hu.bep3.kitchen.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Kitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String restaurantName;
    private String address;

    @OneToOne
    private Stock stock;

//    private List<Employee> employees;

    @ElementCollection
    @CollectionTable(name = "kitchen_menu")
    private List<Long> menu;

    @ElementCollection
    @CollectionTable(name = "pending_orders")
    private List<Long> pendingOrders;

    @ElementCollection
    @CollectionTable(name = "orders")
    private List<Long> ordersInProcess;

    public Kitchen(){ }

    public List<Long> getAllOrderIds() {
        List<Long> orders = new ArrayList<>();

        orders.addAll(pendingOrders);
        orders.addAll(ordersInProcess);

        return orders;
    }

    public void addPendingOrder(Long pendingOrder) {
        this.pendingOrders.add(pendingOrder);
    }

    public Boolean removePendingOrder(Long pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            return this.pendingOrders.add(pendingOrder);
        }
        return false;
    }
}
