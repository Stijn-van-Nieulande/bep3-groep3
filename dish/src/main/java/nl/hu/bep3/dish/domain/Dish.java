package nl.hu.bep3.dish.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Persistable;

public class Dish implements Persistable<UUID> {
  private UUID id;

  private String name;
  private double price;

  private List<IngredientAmount> ingredients = new ArrayList<>();

  public Dish() {}

  public Dish(final String name, final double price, final List<IngredientAmount> ingredients) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.price = price;
    this.ingredients = ingredients;
  }

  public UUID getId() {
    return this.id;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  public List<IngredientAmount> getIngredients() {
    return this.ingredients;
  }

  public void setIngredients(final List<IngredientAmount> ingredients) {
    this.ingredients = ingredients;
  }

  public void addIngredient(final IngredientAmount ingredient) {
    this.ingredients.add(ingredient);
  }

  public boolean removeIngredient(final IngredientAmount ingredient) {
    return this.ingredients.remove(ingredient);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Dish dish = (Dish) o;
    return Double.compare(dish.price, this.price) == 0
        && this.id.equals(dish.id)
        && this.name.equals(dish.name)
        && Objects.equals(this.ingredients, dish.ingredients);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.price, this.ingredients);
  }
}
