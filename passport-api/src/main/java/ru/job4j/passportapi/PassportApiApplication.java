package ru.job4j.passportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PassportApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportApiApplication.class, args);
    }

}
