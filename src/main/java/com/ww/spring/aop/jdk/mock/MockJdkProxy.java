package com.ww.spring.aop.jdk.mock;

import java.lang.reflect.Method;

public class MockJdkProxy {


    public static void main(String[] args) {

        //1、硬编码
//        $Proxy0 proxy = new $Proxy0();
//        proxy.foo();

        //2、灵活编码
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
                // 1. 功能增强
                System.out.println("1. 功能增强 before...");
                // 2. 执行目标方法
//                System.out.println("2. 灵活编码执行目标方法");
//                MockJdkProxy.Target target = new MockJdkProxy.Target();
//                target.foo();
                System.out.println("2. 更灵活编码执行目标方法");
                // 使用 JDK 的动态代理时，会使用反射调用方法。
                // 相比于正常调用方法，利用反射的性能要稍微低一些，JDK 对反射有进行优化吗？ 答案是肯定的，总结就是超过一定次数直接使用类调用方法
                return method.invoke(new Target(), params);
            }
        });
        proxy.foo();
        int bar = proxy.bar();
        System.out.println(bar);

    }

    // 需要代理的接口
    interface Foo {
        void foo();

        int bar();
    }

    // 代理接口目标类
    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target 实现 foo() 执行 ... ");
        }

        @Override
        public int bar() {
            System.out.println("target 实现 bar() 执行 ... ");
            return 100;
        }
    }

    /**
     * 核心：
     * 1、“功能增强”和“调用目标”这两部分的代码都是不确定的，可以提供一个抽象类（InvocationHandler 接口），等到用户具体使用时才实现抽象类，重写抽象方法。
     * 2、调用代理对象中的某个方法时，增强的应该是目标对象中对应的方法，希望在调用目标方法时能够动态编码。
     * 3、还将代理对象作为方法的参数，以便用户根据实际情况使用
     * 4、还可以将代理对象作为方法的参数，以便用户根据实际情况使用。（好像目前没发现有啥用！）
     */
    interface InvocationHandler {
        Object invoke(Object proxy, Method method, Object[] params) throws Throwable;
    }


}
