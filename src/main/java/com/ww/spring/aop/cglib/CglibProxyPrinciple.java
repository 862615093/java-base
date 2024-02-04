package com.ww.spring.aop.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * cglib动态代理原理
 */
@Slf4j
public class CglibProxyPrinciple {

    public static void main(String[] args) throws IOException {
        Proxy proxy = new Proxy();
        Target target = new Target();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object p, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("1... before...");
//                return method.invoke(target, args); // 反射调用
                // FastClass
                return methodProxy.invoke(target, args); // 内部无反射, 结合目标用
//                return methodProxy.invokeSuper(p, args); // 内部无反射, 结合代理用
            }
        });
        proxy.save();

        System.out.println(proxy.getClass());

        System.in.read();
//        proxy.save(1);
//        proxy.save(2L);
    }

}
