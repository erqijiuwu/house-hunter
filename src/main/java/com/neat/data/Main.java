package com.neat.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
//    	int a=0;
    	System.out.println("Hello Fiona 😄!");
        SpringApplication.run(Main.class, args);
    }
}