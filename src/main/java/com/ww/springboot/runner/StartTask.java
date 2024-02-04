package com.ww.frame.springboot.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class StartTask implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----CommandLineRunner:springboot start......");
    }
}
