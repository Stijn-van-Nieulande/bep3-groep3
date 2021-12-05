package nl.hu.bep3.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.management")
public class ManagementApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(ManagementApplication.class, args);
    }
}
