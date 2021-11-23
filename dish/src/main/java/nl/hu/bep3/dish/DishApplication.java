package nl.hu.bep3.dish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.dish")
public class DishApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(DishApplication.class, args);
    }
}
