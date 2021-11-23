package nl.hu.bep3.kitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.kitchen")
public class KitchenApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(KitchenApplication.class, args);
    }
}
