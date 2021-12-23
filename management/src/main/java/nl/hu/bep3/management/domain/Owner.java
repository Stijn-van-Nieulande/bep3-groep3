package nl.hu.bep3.management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Owner {
  @Id private Long id;

  private String firstName;
  private String lastName;
  private Double salaris;
  private Role role;
}
