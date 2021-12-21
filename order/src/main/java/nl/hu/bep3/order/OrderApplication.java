package nl.hu.bep3.order;

import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.order")
@Import(SwaggerDataProviderConfig.class)
@RestController
public class OrderApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(OrderApplication.class, args);
    }

    @GetMapping("/")
    public String home()
    {
        return "Hello from order";
    }
}
