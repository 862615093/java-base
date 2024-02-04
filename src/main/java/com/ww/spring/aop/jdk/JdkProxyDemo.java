package com.ww.spring.aop.jdk;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {

    interface Foo {
        void fo1();

        void fo2();

    }

    static final class Target implements Foo {

        public void fo1() {
            System.out.println("target fo1");
        }

        public void fo2() {
            System.out.println("target fo2");
        }

    }

    // jdk 只能针对接口代理
    public static void main(String[] param) throws IOException {
        // 目标对象
        Target target = new Target();

        ClassLoader loader = JdkProxyDemo.class.getClassLoader(); // 用来加载在运行期间动态生成的字节码

        //proxy 和 target 兄弟关系
        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                // 目标.方法(参数)
                // 方法.invoke(目标, 参数);
                Object result = method.invoke(target, args);
                System.out.println("after....");
                return result; // 让代理也返回目标方法执行的结果
            }
        });

        System.out.println(proxy.getClass());

        proxy.fo1();//传入需要反射的方法
//        proxy.fo2();

        System.in.read();
    }
}
