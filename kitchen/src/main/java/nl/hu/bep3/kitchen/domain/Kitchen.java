package nl.hu.bep3.kitchen.domain;

import nl.hu.bep3.kitchen.domain.exceptions.orderNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Kitchen {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String restaurantName;
    private String address;

//    @OneToOne
    private Stock stock;

//    private List<Employee> employees;

//    @ElementCollection
//    @CollectionTable(name = "kitchen_menu")
    private List<Long> menu;

//    @ElementCollection
//    @CollectionTable(name = "pending_orders")
    private List<Long> pendingOrders;

//    @ElementCollection
//    @CollectionTable(name = "orders")
    private List<Long> ordersInProcess;

    public Kitchen(){ }

    public List<Long> getAllOrderIds() {
        List<Long> orders = new ArrayList<>();

        orders.addAll(pendingOrders);
        orders.addAll(ordersInProcess);

        return orders;
    }

    public void acceptOrder(Long pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
            this.ordersInProcess.add(pendingOrder);
        } else {
            throw new orderNotFoundException(pendingOrder);
        }
    }

    public void removePendingOrder(Long pendingOrder) {
        if (this.pendingOrders.contains(pendingOrder)) {
            this.pendingOrders.remove(pendingOrder);
        }
        throw new orderNotFoundException(pendingOrder);
    }

    public Stock getStock() { return stock; }

    public List<Long> getOrdersInProcess() {
        return ordersInProcess;
    }
}
