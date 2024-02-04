package com.ww.frame.spring.aop.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
@Slf4j
public class CglibProxyDemo {

    static class Target {
        public void foo() {
            log.debug("2...");
            log.debug("target foo");
        }
    }

    // 代理是子类型, 目标是父类型
    public static void main(String[] param) {
        Target target = new Target();
        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (p, method, args, methodProxy) -> {
            log.debug("1...");
            log.debug("before...");
//            Object result = method.invoke(target, args); // 用方法反射调用目标
            // methodProxy 它可以避免反射调用
            Object result = methodProxy.invoke(target, args); // 内部没有用反射, 需要目标 （spring）
//            Object result = methodProxy.invokeSuper(p, args); // 内部没有用反射, 需要代理
            log.debug("3...");
            log.debug("after...");
            return result;
        });
        proxy.foo();
    }
}