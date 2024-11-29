package com.ww.spring.aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class TestAspectj {


    /**
     * 学到了什么  ：
     * 1. AspectJ 编译器增强
     * 2. aop 的原理并非代理一种, 编译器也能玩出花样，属于编译阶段修改字节码将代理逻辑加入进去
     */
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(TestAspectj.class, args);
        MyService service = context.getBean(MyService.class);

        log.debug("service class: {}", service.getClass());

        service.foo();

        context.close();

//        new MyService().foo();


    }



}
