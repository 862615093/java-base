package com.ww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaBaseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JavaBaseApplication.class, args);
    }
}