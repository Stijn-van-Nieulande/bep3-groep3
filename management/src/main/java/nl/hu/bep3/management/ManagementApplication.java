package nl.hu.bep3.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.management")
@RestController
public class ManagementApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @GetMapping("/")
    public String home()
    {
        return "Hello from management";
    }
}
