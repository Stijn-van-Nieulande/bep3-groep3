package nl.hu.bep3.dish.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Dish {

  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;
  private double price;

  //  @OneToMany
//  @JoinTable(
//          name = "ingredient_amounts_in_dish",
//          joinColumns = @JoinColumn( name="dish_id"),
//          inverseJoinColumns = @JoinColumn( name="ingredient_amount_id")
//  )
  private List<IngredientAmount> ingredients = new ArrayList<IngredientAmount>();

  public Dish() {
  }

  public Dish(String name, double price, List<IngredientAmount> ingredients) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.price = price;
    this.ingredients = ingredients;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public List<IngredientAmount> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<IngredientAmount> ingredients) {
    this.ingredients = ingredients;
  }

  public void addIngredient(IngredientAmount ingredient) {
    this.ingredients.add(ingredient);
  }

  public boolean removeIngredient(IngredientAmount ingredient) {
    return ingredients.remove(ingredient);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Dish dish = (Dish) o;
    return Double.compare(dish.price, price) == 0 && id.equals(dish.id) && name.equals(dish.name)
        && Objects.equals(ingredients, dish.ingredients);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, ingredients);
  }
}
