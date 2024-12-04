package org.springframework.aop.framework.autoproxy;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;

import java.util.List;

@Slf4j
public class TestAnnotationAwareAspectJAutoProxyCreator {

//    public static void main(String[] args) {
//
//        GenericApplicationContext context = new GenericApplicationContext();
//
//        context.registerBean("aspect1", Aspect1.class);
//        context.registerBean("config", Config.class);
//        context.registerBean(ConfigurationClassPostProcessor.class);
//
//        context.refresh();
//        System.out.println("---------------------------------------------------------");
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//        System.out.println("---------------------------------------------------------");
//        context.close();
//    }

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        context.refresh();

        /**
         * spring 中存在一个名为 AnnotationAwareAspectJAutoProxyCreator 的 Bean 后置处理器, 尽管它的名称中没有 BeanPostProcessor 的字样，但它确实是实现了 BeanPostProcessor 接口的。
         *
         * AnnotationAwareAspectJAutoProxyCreator 主要作用：
         *      1、找到容器中所有的切面，针对高级切面，将其转换为低级切面。
         *      2、根据切面信息，利用 ProxyFactory 创建代理对象。
         *      3、AnnotationAwareAspectJAutoProxyCreator 实现了 BeanPostProcessor，可以在 Bean 生命周期中的一些阶段对 Bean 进行拓展。
         *      4、AnnotationAwareAspectJAutoProxyCreator 可以在 Bean 进行 依赖注入之前、Bean 初始化之后 对 Bean 进行拓展。
         */
        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);

        /**
         * findEligibleAdvisors() 方法作用：找出切面信息
         * 接收两个参数：
         * beanClass：配合切面使用的目标类 Class 信息
         * beanName：当前被代理的 Bean 的名称
         *
         * 打印出 4 个能配合 Target1 使用的切面信息，其中：
         * 第一个切面 ExposeInvocationInterceptor.ADVISOR 是 Spring 为每个代理对象都会添加的切面；
         * 第二个切面 DefaultPointcutAdvisor 是自行编写的低级切面；
         * 第三个和第四个切面 InstantiationModelAwarePointcutAdvisor 是由高级切面转换得到的两个低级切面。
         */
//        // 获取能够配合 Target1 使用的切面
          // 测试 findEligibleAdvisors 方法
//        List<Advisor> advisors = creator.findEligibleAdvisors(Target1.class, "target1");
//        System.out.println("----------------------------------------------------------");
//        advisors.forEach(System.out::println);
//        System.out.println("----------------------------------------------------------");


        System.out.println("----------------------------------------------------------");
        /**
         * wrapIfNecessary() 方法作用：是否需要使用代理对象
         * 内部调用了 findEligibleAdvisors() 方法，若 findEligibleAdvisors() 方法返回的集合不为空，则表示需要创建代理对象。
         * 如果需要创建对象，wrapIfNecessary() 方法返回的是代理对象，否则仍然是原对象。
         *
         * wrapIfNecessary() 方法接收三个参数：
         * bean：原始 Bean 实例
         * beanName：Bean 的名称
         * cacheKey：用于元数据访问的缓存 key
         */
        Object o1 = creator.wrapIfNecessary(new Target1(), "target1", "target1");
        System.out.println(o1.getClass());// class org.springframework.aop.framework.autoproxy.A17$Target1$$EnhancerBySpringCGLIB$$634976f6
        Object o2 = creator.wrapIfNecessary(new Target2(), "target2", "target2");
        System.out.println(o2.getClass());// class org.springframework.aop.framework.autoproxy.A17$Target2
        System.out.println("----------------------------------------------------------");
        ((Target1) o1).foo();
        System.out.println("----------------------------------------------------------");
//        ((Target2) o2).bar();


        /**
         * 切面的顺序控制（数字越小优先级越高）:
         * 1、在高级切面中，@Order 只有放在类上才生效，放在方法上不会生效。比如高级切面中有多个前置通知，这些前置通知对应的方法上使用 @Order 注解是无法生效的。
         * 2、针对低级切面，需要设置 advisor 的 order 值，而不是向高级切面那样使用 @Order 注解，使用 @Order 注解设置在 advisor3() 方法上是无用的。
         */
        ((Target1) o1).foo();

        context.close();
    }





    /**
     * 高级切面
     */
    @Aspect
    @Order(1)
    static class Aspect1 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect1 before...");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect1 after...");
        }
    }

    /**
     * 低级切面(利用配置类实现)，由一个切点和一个通知组成
     */
    @Configuration
    static class Config {

        @Bean
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice3);
            // 设置切面执行顺序
            advisor.setOrder(2);
            return advisor;
        }

        @Bean
        public MethodInterceptor advices() {
            return invocation -> {
                System.out.println("advice3 before...");
                Object result = invocation.proceed();
                System.out.println("advice3 after...");
                return result;
            };
        }
    }

    static class Target1 {
        public void foo() {
            System.out.println("target1 foo");
        }
    }

    static class Target2 {
        public void bar() {
            System.out.println("target2 bar");
        }
    }

}
