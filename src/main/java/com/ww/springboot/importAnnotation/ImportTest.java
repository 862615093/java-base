package com.ww.springboot.importAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(com.ww.springboot.importAnnotation.AppConfig.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        System.out.println("--------------------------------------------------------");
        for (String beanDefinitionName: beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("--------------------------------------------------------");
    }
}
