package com.ww.spring.aop.agent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class TestAgent {


    /**
     *
     *  注意几点
     *  1. . 运行时需要在 VM options 里加入 -javaagent:D:/Java/repo/org/aspectj/aspectjweaver/1.9.7/aspectjweaver-1.9.7.jar
     *
     *  学到了什么
     *  1. aop 的原理并非代理一种, agent 也能, 只要字节码变了, 行为就变了
     *  2. 查看 MyService 对应的 class 文件，会发现其内容并没有被修改，可以断定不是编译时增强，这里是在类加载时增强。
     *
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestAgent.class, args);
        MyService service = context.getBean(MyService.class);


        System.out.println("===================================================================");

        // ⬇️MyService 并非代理, 但 foo 方法也被增强了, 做增强的 java agent, 在加载类时, 修改了 class 字节码
        log.debug("service class: {}", service.getClass());
        service.foo();

        System.out.println("===================================================================");

        context.close();
    }



}
