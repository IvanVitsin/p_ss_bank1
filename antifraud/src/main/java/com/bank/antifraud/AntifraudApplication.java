package com.bank.antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableEurekaClient
public class AntifraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntifraudApplication.class, args);
    }
}
