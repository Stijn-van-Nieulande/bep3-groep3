package nl.hu.bep3.kitchen.application.response;

import java.util.UUID;

public class OrderDto {

  public UUID id;
  public String status;

  public OrderDto(final UUID id, final String status) {
    this.id = id;
    this.status = status;
  }

  public OrderDto() {}
}
