package nl.hu.bep3.dish;

import com.google.gson.Gson;
import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.dish")
@Import(SwaggerDataProviderConfig.class)
public class DishApplication
{
    public static final Gson GSON = new Gson();

    public static void main(final String[] args)
    {
        SpringApplication.run(DishApplication.class, args);
    }
}
