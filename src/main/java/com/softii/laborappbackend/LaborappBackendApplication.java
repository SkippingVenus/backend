package com.softii.laborappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.softii.laborappbackend")
public class LaborappBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaborappBackendApplication.class, args);
    }
}