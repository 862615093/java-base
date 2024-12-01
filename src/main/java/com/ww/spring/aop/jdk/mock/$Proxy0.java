package com.ww.spring.aop.jdk.mock;

import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class $Proxy0 implements MockJdkProxy.Foo {

    final MockJdkProxy.InvocationHandler h;

    public $Proxy0(MockJdkProxy.InvocationHandler h) {
        this.h = h;
    }

    @Override
    @SneakyThrows
    public void foo() {
        /***********************************1、最开始 硬编码*************************************/
//        // 1. 功能增强
//        System.out.println("1. 功能增强 before...");
//        // 2. 执行目标方法
//        System.out.println("2. 硬编码执行目标方法");
//        MockJdkProxy.Target target = new MockJdkProxy.Target();
//        target.foo();

        /***********************************2、业务代码灵活编码*************************************/
//        Method foo = MockJdkProxy.Foo.class.getMethod("foo");
        h.invoke(this, foo, new Object[0]);

    }

    @Override
    @SneakyThrows
    public int bar() {
//        Method bar = MockJdkProxy.Foo.class.getMethod("bar");
        return (int) h.invoke(this, bar, new Object[0]);
    }

    static Method foo;
    static Method bar;

    // 每调用一次代理对象中的方法都会创建一个 Method 实例，这些实例是可以复用的，因此可以将这些实例的创建移动到静态代码块中：
    static {
        try {
            foo = MockJdkProxy.Foo.class.getMethod("foo");
            bar = MockJdkProxy.Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
