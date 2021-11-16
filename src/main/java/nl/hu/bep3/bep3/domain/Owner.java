package nl.hu.bep3.bep3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Owner
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName = "Erik";
    private String lastName = "Meijer";

    private List<Kitchen> kitchens;
}
