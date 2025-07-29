package ru.starter.byshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "ru.starter.byshop",
        "ru.starter.synthetichumancorestarter"
})
@SpringBootApplication
public class ByshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByshopApplication.class, args);
    }

}
