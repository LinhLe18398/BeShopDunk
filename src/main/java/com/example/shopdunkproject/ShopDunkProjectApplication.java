package com.example.shopdunkproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopDunkProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDunkProjectApplication.class, args);
        System.out.println("http://localhost:8090/products");
    }

}
