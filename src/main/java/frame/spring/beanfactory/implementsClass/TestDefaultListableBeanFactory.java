package frame.spring.beanfactory.implementsClass;

import frame.spring.beanfactory.TestBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
public class TestDefaultListableBeanFactory {


    /**
     * 学到什么：
     * 1、什么是 BeanFactory？
     *    1） 它是 ApplicationContext 的父接口
     *    2） 它才是 Spring 的核心容器，主要的 ApplicationContext 实现组合了它的功能。（常用的 context.getBean("xxx") 方法，其实是调用了 BeanFactory 的 getBean() 方法。
     *
     * 2、BeanFactory 作用？
     *    1） 通过这些方法定义可知，BeanFactory 表面上只有 getBean() 方法，但实际上 Spring 中的控制反转、基本的依赖注入、乃至 Bean 的生命周期中的各个功能都是由它的**实现类**提供。
     *        例如：DefaultListableBeanFactory 实现了 BeanFactory 接口，它能管理 Spring 中所有的 Bean，当然也包含 Spring 容器中的那些单例对象。
     *             DefaultListableBeanFactory 还继承了 DefaultSingletonBeanRegistry 类，这个类就是用来管理 Spring 容器中的单例对象。
     *
     * 3、beanFactory 不会做的事？
     *    1） 不会主动调用 BeanFactory 后处理器
     *    2） 不会主动添加 Bean 后处理器
     *    3） 不会主动初始化单例
     *
     * 4、BeanFactory 接口体系：
     *    1） ListableBeanFactory：提供获取 Bean 集合的能力，比如一个接口可能有多个实现，通过该接口下的方法就能获取某种类型的所有 Bean。
     *    2） HierarchicalBeanFactory：Hierarchical 意为“层次化”，通常表示一种具有层级结构的概念或组织方式，这种层次化结构可以通过父子关系来表示对象之间的关联，比如树、图、文件系统、组织架构等。根据该接口下的方法可知，能够获取到父容器，说明 BeanFactory 有父子容器概念；
     *    3） AutowireCapableBeanFactory：提供了创建 Bean、自动装配 Bean、属性填充、Bean 初始化、依赖注入等能力，比如 @Autowired 注解的底层实现就依赖于该接口的 resolveDependency() 方法；
     *    4） ConfigurableBeanFactory：该接口并未直接继承至 BeanFactory，而是继承了 HierarchicalBeanFactory。Configurable 意为“可配置的”，就是说该接口用于对 BeanFactory 进行一些配置，比如设置类型转换器。
     *
     *
     */
    public static void main(String[] args) {

        // 1、定义一个bean工厂，BeanFactory 的一个实现类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2、bean 的定义（class，scope，初始化，销毁） == BeanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(Config.class)
                .setScope("singleton")
                .getBeanDefinition();

        // 3、定义的 Bean 注册到 bean工厂
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 打印结果：只有 config （很明显是现在的 BeanFactory 缺少了解析 @Configuration 和 @Bean 两个注解的能力。
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 4、注册BeanFactory 和 Bean 后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
//        config
//        org.springframework.context.annotation.internalConfigurationAnnotationProcessor （BeanFactoryPostProcessor）
//        org.springframework.context.event.internalEventListenerProcessor （BeanFactoryPostProcessor）
//        org.springframework.context.event.internalEventListenerFactory  （BeanFactoryPostProcessor）
//        org.springframework.context.annotation.internalAutowiredAnnotationProcessor （BeanPostProcessor）
//        org.springframework.context.annotation.internalCommonAnnotationProcessor （BeanPostProcessor）
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 5、使用BeanFactory后置处理器
        // 作用：将配置类中定义的 Bean 信息补充到 BeanFactory 中
        // ConfigurationClassPostProcessor --> 解析 @Configuration 和 @Bean 注解
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class)
                .values().forEach(beanFactoryPostProcessor -> {
            System.out.println("获取的BeanFactoryPostProcessor: " + beanFactoryPostProcessor);;
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory); // ConfigurationClassPostProcessor、EventListenerMethodProcessor
        });
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
//        config
//        bean1
//        bean2
//        org.springframework.context.annotation.internalConfigurationAnnotationProcessor
//        org.springframework.context.annotation.internalAutowiredAnnotationProcessor
//        org.springframework.context.annotation.internalCommonAnnotationProcessor
//        org.springframework.context.event.internalEventListenerProcessor
//        org.springframework.context.event.internalEventListenerFactory
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 查看 bean2 是否成功注入到 bean1 中
//        System.out.println("bean2 >>>>>>> " + beanFactory.getBean(Bean1.class).getBean2());

        // 6、使用Bean后置处理器
        // 作用： 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
        // internalAutowiredAnnotationProcessor --> 解析 @Autowired 注解 , internalCommonAnnotationProcessor --> 解析 @Resource 注解
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanPostProcessor -> {
            System.out.println("获取的BeanPostProcessor: " + beanPostProcessor); // AutowiredAnnotationBeanPostProcessor、 CommonAnnotationBeanPostProcessor
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // 再次查看 bean2 是否成功注入到 bean1 中
        beanFactory.preInstantiateSingletons(); // 预先初始化单例对象（完成依赖注入和初始化流程） ， 避免懒加载
        System.out.println("bean2 >>>>>>> " + beanFactory.getBean(Bean1.class).getBean2());


    }




    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        public Bean1() {
            log.debug("构造 Bean1()");
        }
    }

    static class Bean2 {
        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }


}
