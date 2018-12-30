package com.neat.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) {
//    	int a=0;
        SpringApplication.run(Main.class, args);

        System.out.println("Hello Fiona 😄!");

//        PropertyListController.search();
    }


}

