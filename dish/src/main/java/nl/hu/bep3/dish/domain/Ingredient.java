package nl.hu.bep3.dish.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Persistable;

public class Ingredient implements Persistable<UUID> {

  private UUID id;
  private String name;
  private List<FoodAllergy> allergies = new ArrayList<>();

  public Ingredient() {}

  public Ingredient(final String name, final List<FoodAllergy> allergies) {
    this.id = UUID.randomUUID();
    this.name = name;
    if (allergies != null) {
      this.allergies = allergies;
    }
  }

  public UUID getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<FoodAllergy> getAllergies() {
    return this.allergies;
  }

  public void setAllergies(final List<FoodAllergy> allergies) {
    this.allergies = allergies;
  }

  public boolean addAllergy(final FoodAllergy foodAllergy) {
    if (!this.allergies.contains(foodAllergy)) {
      return this.allergies.add(foodAllergy);
    }
    return false;
  }

  public boolean removeAllergy(final FoodAllergy foodAllergy) {
    if (this.allergies.contains(foodAllergy)) {
      return this.allergies.remove(foodAllergy);
    }
    return false;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Ingredient that = (Ingredient) o;
    return this.id.equals(that.id)
        && this.name.equals(that.name)
        && Objects.equals(this.allergies, that.allergies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.allergies);
  }
}
