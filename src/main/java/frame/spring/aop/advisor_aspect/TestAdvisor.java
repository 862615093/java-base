package frame.spring.aop.advisor_aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class TestAdvisor {


    /**
     * 切点：即 Pointcut，其典型实现是 AspectJExpressionPointcut
     * 通知：即 Advice，其典型子类接口为 MethodInterceptor，表示环绕通知
     * 切面：即 Advisor，其典型实现是 DefaultPointcutAdvisor, 仅包含一个切点和通知
     * <p>
     * <p>
     * <p>
     * 1、切面有 aspect 和 advisor 两个概念，aspect 是多组通知（advice）和切点（pointcut）的组合，也是实际编码时使用的。
     * 2、advisor 则是更细粒度的切面，仅包含一个通知和切点，aspect 在生效之前会被拆解成多个 advisor。
     *       两个切面概念：
     *       aspect = 通知 1 （advice） + 切点 1（pointcut）、 通知 2 （advice） + 切点 2（pointcut） ....
     *       advisor = 更细粒度的切面，包含一个通知和切点
     * 3、创建代理对象时，无需显式实现 JDK 动态代理或 CGLib 动态代理，Spring 提供了名为 ProxyFactory 的工厂，其内部通过不同的情况选择不同的代理实现，更方便地创建代理对象。
     */
    public static void main(String[] args) {

        //1、备好切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (com.ww.spring.aop.advisor.* foo())");

        //2、备好通知
        MethodInterceptor advice = new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
                System.out.println("before...");
                Object result = invocation.proceed();
                System.out.println("after...");
                return result;
            }
        };

        //3、备好切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

        //4、创建代理
        Target1 target = new Target1();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        factory.addAdvisor(advisor);

        /**
         * ProxyFactory 的父类 ProxyConfig 中有个名为 proxyTargetClass 的布尔类型成员变量：
         * 1、当 proxyTargetClass == false，并且目标对象所在类实现了接口时，将选择 JDK 动态代理；
         * 2、当 proxyTargetClass == false，但目标对象所在类未实现接口时，将选择 CGLib 动态代理；
         * 3、当 proxyTargetClass == true，总是选择 CGLib 动态代理。
         */
        factory.setInterfaces(target.getClass().getInterfaces());
//        factory.setProxyTargetClass(false);
        I1 proxy = (I1) factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
    }


    interface I1 {
        void foo();

        void bar();
    }

    static class Target1 implements I1 {

        @Override
        public void foo() {
            System.out.println("target1 foo");
        }

        @Override
        public void bar() {
            System.out.println("target1 bar");
        }
    }

    static class Target2 {
        public void foo() {
            System.out.println("target2 foo");
        }

        public void bar() {
            System.out.println("target2 bar");
        }
    }

}
