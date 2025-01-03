package frame.spring.beanfactory;

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

@Slf4j
public class TestBeanFactory {



    public static void main(String[] args) {

        // DefaultListableBeanFactory 作为 BeanFactory 最重要的实现类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // bean 的定义（class, scope, 初始化, 销毁）
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder
                        .genericBeanDefinition(Config.class)
                        .setScope("singleton")
                        .getBeanDefinition();

        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 给 BeanFactory 添加一些常用的  BeanFactory后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // BeanFactory后处理器主要功能：补充了一些 bean 定义（说白了就是BeanFactory的增强功能,例如：internalConfigurationAnnotationProcessor）
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()
                .forEach(beanFactoryPostProcessor -> {
            log.debug("{}", beanFactoryPostProcessor);
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        System.out.println("---------------------------------------------------");


        // Bean 后处理器 （例如：internalAutowiredAnnotationProcessor、internalCommonAnnotationProcessor）,
        // 作用： 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
        // internalAutowiredAnnotationProcessor 和 internalCommonAnnotationProcessor 的两个后置处理器。前者用于解析 @Autowired 注解，后者用于解析 @Resource 注解，添加到 BeanFactory 中的后置处理器里
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream()
//                .sorted(beanFactory.getDependencyComparator())
                .forEach(beanPostProcessor -> {
                    System.out.println("beanPostProcessor >>>> " + beanPostProcessor);
                    beanFactory.addBeanPostProcessor(beanPostProcessor);
                });




        System.out.println("---------------------------------------------------");

        // 查看容器里有哪些bean
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }



        System.out.println("---------------------------------------------------");

        beanFactory.preInstantiateSingletons(); // 准备好所有单例 ， 避免懒加载

        // 注册Bean后处理器，前后看下能否取到bean2
        System.out.println("bean2 >>>>>>> " + beanFactory.getBean(Bean1.class).getBean2());

        /*
            学到了什么:
            a. beanFactory 不会做的事
                   1. 不会主动调用 BeanFactory 后处理器
                   2. 不会主动添加 Bean 后处理器
                   3. 不会主动初始化单例
                   4. 不会解析beanFactory 还不会解析 ${ } 与 #{ }
            b. bean 后处理器会有排序的逻辑
         */


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

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
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

    static class Bean3 {

    }

    static class Bean4 {

    }

}
