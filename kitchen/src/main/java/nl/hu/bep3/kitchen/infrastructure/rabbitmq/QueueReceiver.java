package nl.hu.bep3.kitchen.infrastructure.rabbitmq;

import nl.hu.bep3.kitchen.domain.service.KitchenService;

public class QueueReceiver {

  private KitchenService kitchenService;

  public QueueReceiver(KitchenService kitchenService) {
    this.kitchenService = kitchenService;
  }

  public void test() {

    // @RabbitListener(queues = "order.addOrder")
    //public void addNewOrder(String message){
    //message to Dto
    //kitchenService.
  }
}
