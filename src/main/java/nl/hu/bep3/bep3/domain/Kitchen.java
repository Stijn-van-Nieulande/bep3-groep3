package nl.hu.bep3.bep3.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Kitchen
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String restaurantName;
    private String adress;

    private Stock stock;
    private List<Dish> menu = new ArrayList<>();
    private List<Employee> Employees = new ArrayList();
}
