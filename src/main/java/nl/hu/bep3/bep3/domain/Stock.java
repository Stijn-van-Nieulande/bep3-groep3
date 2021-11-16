package nl.hu.bep3.bep3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Stock
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer capacity;
    private Map<Ingredient, Integer> ingredients;


    public Stock(final Integer capacity, final Map<Ingredient, Integer> ingredients)
    {
        this.capacity = capacity;
        this.ingredients = ingredients;
    }

    public Stock()
    {
    }

    @Id
    public Long getId()
    {
        return this.id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }
}
