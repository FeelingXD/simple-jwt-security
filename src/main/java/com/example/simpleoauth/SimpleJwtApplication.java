package com.example.simpleoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SimpleJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleJwtApplication.class, args);
    }
}
