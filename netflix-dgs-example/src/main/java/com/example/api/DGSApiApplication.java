package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DGSApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DGSApiApplication.class, args);
    }
}
