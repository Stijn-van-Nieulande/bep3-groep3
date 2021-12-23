package nl.hu.bep3.kitchen.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.exceptions.OrderNotFoundException;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Kitchen {

  private ArrayList<UUID> menu = new ArrayList<>();
  private ArrayList<UUID> pendingOrders = new ArrayList<>();
  private ArrayList<UUID> ordersInProcess = new ArrayList<>();
  private UUID id;
  private String restaurantName;
  private String address;
  private Storage storage;

  public Kitchen() {}

  public Kitchen(final String restaurantName, final String address, final Storage storage) {
    Objects.requireNonNull(restaurantName, "Restaurant name cannot be null!");
    Objects.requireNonNull(address, "Address cannot be null!");
    Objects.requireNonNull(storage, "stock cannot be null!");

    this.id = UUID.randomUUID();
    this.restaurantName = restaurantName;
    this.address = address;
    this.storage = storage;
  }

  public List<UUID> getAllOrderIds() {
    final List<UUID> orders = new ArrayList<>();

    orders.addAll(this.pendingOrders);
    orders.addAll(this.ordersInProcess);

    return orders;
  }

  public UUID getId() {
    return this.id;
  }

  public Boolean addPendingOrder(final UUID orderId) {
    return this.pendingOrders.add(orderId);
  }

  public void acceptOrder(final UUID pendingOrder) {
    if (this.pendingOrders.contains(pendingOrder)) {
      this.pendingOrders.remove(pendingOrder);
      this.ordersInProcess.add(pendingOrder);
      System.out.println("Accepted");
    } else {
      System.out.println("accept error");
      throw new OrderNotFoundException(pendingOrder);
    }
  }

  public void removePendingOrder(final UUID pendingOrder) {
    if (this.pendingOrders.contains(pendingOrder)) {
      this.pendingOrders.remove(pendingOrder);
      System.out.println("Removed");
    } else {
      System.out.println("remove error");
      throw new OrderNotFoundException(pendingOrder);
    }
  }

  public Storage getStorage() {
    return this.storage;
  }

  public List<UUID> getOrdersInProcess() {
    return this.ordersInProcess;
  }

  public List<UUID> getMenu() {
    return this.menu;
  }

  public Boolean addToMenu(final UUID dish) {
    if (!this.menu.contains(dish)) {
      return this.menu.add(dish);
    }
    return false;
  }

  public String getRestaurantName() {
    return this.restaurantName;
  }

  public void setRestaurantName(final String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Kitchen kitchen = (Kitchen) o;
    return this.id.equals(kitchen.id)
        && this.restaurantName.equals(kitchen.restaurantName)
        && this.address.equals(kitchen.address)
        && Objects.equals(this.storage, kitchen.storage)
        && Objects.equals(this.menu, kitchen.menu)
        && Objects.equals(this.pendingOrders, kitchen.pendingOrders)
        && Objects.equals(this.ordersInProcess, kitchen.ordersInProcess);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.id,
        this.restaurantName,
        this.address,
        this.storage,
        this.menu,
        this.pendingOrders,
        this.ordersInProcess);
  }
}
