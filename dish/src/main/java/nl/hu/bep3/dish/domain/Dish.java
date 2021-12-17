package nl.hu.bep3.dish.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Dish {
  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private double price;

//  @OneToMany
//  @JoinTable(
//          name = "ingredient_amounts_in_dish",
//          joinColumns = @JoinColumn( name="dish_id"),
//          inverseJoinColumns = @JoinColumn( name="ingredient_amount_id")
//  )
  private List<IngredientAmount> ingredients = new ArrayList<IngredientAmount>();

  public Dish() {}

  public Dish(String name, double price, List<IngredientAmount> ingredients) {
    this.name = name;
    this.price = price;
    this.ingredients = ingredients;
  }

  public Long getId() {
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
}
