package nl.hu.bep3.dish;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.dish")
public class DishApplication
{
    public static final Gson GSON = new Gson();

    public static void main(final String[] args)
    {
        SpringApplication.run(DishApplication.class, args);
    }
}
