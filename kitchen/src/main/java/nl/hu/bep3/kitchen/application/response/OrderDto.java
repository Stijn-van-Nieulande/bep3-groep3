package nl.hu.bep3.kitchen.application.response;

import java.util.UUID;

public class OrderDto {
  public UUID id;
  public String status;

  public OrderDto(UUID id, String status){
    this.id = id;
    this.status = status;
  }

  public OrderDto(){}
}
