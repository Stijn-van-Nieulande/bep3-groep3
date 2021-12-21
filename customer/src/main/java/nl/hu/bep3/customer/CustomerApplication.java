package nl.hu.bep3.customer;

import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.customer")
@Import(SwaggerDataProviderConfig.class)
public class CustomerApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
