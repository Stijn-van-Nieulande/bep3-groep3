package nl.hu.bep3.kitchen;

import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.kitchen")
@Import(SwaggerDataProviderConfig.class)
public class KitchenApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(KitchenApplication.class, args);
    }
}
