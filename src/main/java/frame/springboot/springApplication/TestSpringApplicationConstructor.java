package frame.springboot.springApplication;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
public class TestSpringApplicationConstructor {

    @SneakyThrows
    public static void main(String[] args) {

        /**
         * 学到了什么： SpringApplication 的构造方法具体做了什么，查看源码底层主要做了如下几件事：
         * 1、获取 Bean Definition 源
         * 2、推断应用类型
         * 3、添加 ApplicationContext 初始化器（ApplicationContextInitializer） --> 作用 : 在创建 ApplicationContext，到 ApplicationContext 的 refresh() 方法完成之间做一些拓展功能。
         * 4、添加事件监听器
         * 5、主类推断
         */
        // 创建并初始化 Spring 容器
        SpringApplication spring = new SpringApplication(TestSpringApplicationConstructor.class);

//        使用 XML 配置文件添加 Bean，并利用 setSources() 方法设置创建 ApplicationContext 的其他源
        spring.setSources(Collections.singleton("classpath:b01.xml"));


        // 添加 ApplicationContext 初始化器
        // 源码：从配置文件中读取初始化器
        // setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
        // 模拟
        spring.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if (applicationContext instanceof GenericApplicationContext) {
                    GenericApplicationContext context = (GenericApplicationContext) applicationContext;
                    context.registerBean("bean3", Bean3.class);
                }
            }
        });


        // 推断应用类型
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("应用类型为: " + deduceFromClasspath.invoke(null));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


        // 添加事件监听器
        // 源码：setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        // 调用 SpringApplication 对象的 addListeners() 方法添加自定义事件监听器
        System.out.println("<<<<<<<<<<<<<<<<<<< 自定义事件监听器 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        spring.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("线程  " + Thread.currentThread().getName() + " 收到类型为：ApplicationEvent 的事件：" + event);
            }
        });
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< 自定义事件监听器 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 最后跑
        ConfigurableApplicationContext context = spring.run(args);

        // 获取 Bean Definition 源
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< 获取 Bean Definition 源<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        Arrays.stream(context.getBeanDefinitionNames()).forEach(i -> {
            System.out.println("BeanDefinitionName: " + i + " -->  来源: " + context.getBeanFactory().getBeanDefinition(i).getResourceDescription());
        });
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 获取 Bean Definition 源< <<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 主类推断
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("\t主类是: " + deduceMainApplicationClass.invoke(spring));


        context.close();

    }


    static class Bean1 {
    }

    static class Bean2 {
    }

    static class Bean3 {
    }

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
