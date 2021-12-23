package nl.hu.bep3.kitchen.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Order-service", url = "${app.feign.order.url}")
public interface OrderServiceProxy {

}
