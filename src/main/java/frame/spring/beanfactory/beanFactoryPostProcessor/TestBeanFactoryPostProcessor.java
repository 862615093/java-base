package frame.spring.beanfactory.beanFactoryPostProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class TestBeanFactoryPostProcessor {

    /**
     * BeanFactory 后处理器的作用
     *
     * 学到了什么
     *     a. @ComponentScan, @Bean, @Mapper 等注解的解析属于核心容器(即 BeanFactory)的扩展功能
     *     b. 这些扩展功能由不同的 BeanFactory 后处理器来完成, 其实主要就是补充了一些 bean 定义
     *
     *  常见的beanfactory后置处理器：
     *  1、ConfigurationClassPostProcessor ： @ComponentScan @Bean @Import @ImportResource
     *  2、MapperScannerConfigurer ： @MapperScanner
     *
     */
    public static void main(String[] args) {

        // 1、GenericApplicationContext 是一个【干净】的容器
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);


        // 2、注册spring常见的beanfactory后置处理器
        context.registerBean(ConfigurationClassPostProcessor.class);   // 解析 @ComponentScan @Bean @Import @ImportResource
//
//        context.registerBean(MapperScannerConfigurer.class, bd -> {    // 解析 @MapperScanner
//            bd.getPropertyValues().add("basePackage", "com.ww.spring.beanfactory.beanFactoryPostProcessor.mapper");
//        });


        // 3、自定义 beanfactory后置处理器
//        context.registerBean(ComponentScanPostProcessor.class); // 解析 @ComponentScan

//        context.registerBean(AtBeanPostProcessor.class); // 解析 @Bean

//        context.registerBean(MapperPostProcessor.class); // 解析 Mapper 接口

        // 4、初始化容器
        context.refresh();

        // 查看容器中有哪些bean
        System.out.println("--------------------------------------");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("--------------------------------------");

//        Mapper1 mapper1 = context.getBean(Mapper1.class);
//        Mapper2 mapper2 = context.getBean(Mapper2.class);

        // 5、销毁容器
        context.close();


    }



}
