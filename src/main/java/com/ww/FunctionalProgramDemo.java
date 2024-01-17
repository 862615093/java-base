package com.ww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FunctionalProgramDemo {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FunctionalProgramDemo.class, args);
    }
}