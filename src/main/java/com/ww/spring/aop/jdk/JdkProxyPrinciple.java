package com.ww.frame.spring.aop.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理原理
 */
@Slf4j
public class JdkProxyPrinciple {


    public static void main(String[] param) {
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                log.debug("2.....");
                // 1. 功能增强
                log.debug("before...");
                // 2. 调用目标
//                new Target().foo();
                return method.invoke(new Target(), args); //反射
            }
        });
        proxy.foo();
//        proxy.bar();
        /*
            学到了什么: 代理一点都不难, 无非就是利用了多态、反射的知识
                1. 方法重写可以增强逻辑, 只不过这【增强逻辑】千变万化, 不能写死在代理内部
                2. 通过接口回调将【增强逻辑】置于代理类之外
                3. 配合接口方法反射(也是多态), 就可以再联动调用目标方法
         */
    }

    interface Foo {
        void foo();
        int bar();
    }

    //目标类
    static class Target implements Foo {
        public void foo() {
            log.debug("3.....");
            log.debug("target foo");
        }

        @Override
        public int bar() {
            System.out.println("target bar");
            return 100;
        }
    }
}
