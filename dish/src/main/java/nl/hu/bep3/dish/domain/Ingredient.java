package nl.hu.bep3.dish.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document
public class Ingredient {
  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

//  @ElementCollection(targetClass = FoodAllergy.class)
//  @JoinTable(name = "ingredientAllergies", joinColumns = @JoinColumn(name = "ingredient_id"))
//  @Column(name = "allergies", nullable = false)
//  @Enumerated(EnumType.ORDINAL)
  private List<FoodAllergy> allergies = new ArrayList<>();

  public Ingredient() {}

  public Ingredient(String name, List<FoodAllergy> allergies) {
    this.name = name;
    if (allergies != null) {
      this.allergies = allergies;
    }
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

  public List<FoodAllergy> getAllergies() {
    return allergies;
  }

  public boolean addAllergy(FoodAllergy foodAllergy){
    if (!allergies.contains(foodAllergy)) {
      return allergies.add(foodAllergy);
    }
    return false;
  }

  public boolean removeAllergy(FoodAllergy foodAllergy){
    if (allergies.contains(foodAllergy)) {
      return allergies.remove(foodAllergy);
    }
    return false;
  }
}
