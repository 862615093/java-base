package frame.spring.aop.jdk;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
    学到了什么: jdk代理底层实现，一点都不难, 无非就是利用了多态、反射的知识
        1. 方法重写可以增强逻辑, 只不过这【增强逻辑】千变万化, 不能写死在代理内部
        2. 通过接口回调将【增强逻辑】置于代理类之外
        3. 配合接口方法反射(也是多态), 就可以再联动调用目标方法
 */
public class TestJdkProxy {

    // 需要代理的接口
    interface Foo {
        void fo1();

        int fo2();

    }

    // 实现代理接口的目标类
    static final class Target implements Foo {

        public void fo1() {
            System.out.println("target fo1");
        }

        public int fo2() {
            System.out.println("target fo2");
            return 100;
        }

    }

    /**
     * 1、jdk 只能针对接口代理。
     * 2、代理对象和目标对象是兄弟关系，都实现了相同的接口，因此不能将代理对象强转成目标对象类型。
     * 3、代理类与目标类之间没有继承关系，因此目标类可以被 final 修饰。
     */
    public static void main(String[] param) throws IOException {

        // 目标对象
        Target target = new Target();

        // 用来加载在运行期间动态生成的字节码
        ClassLoader loader = TestJdkProxy.class.getClassLoader();

        //重点：jdk生成的 proxy代理类（asm技术运行期间动态生成字节码） 和 Target类 是 兄弟关系！！！
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

        System.out.println(proxy.getClass()); // com.ww.spring.aop.jdk.$Proxy0

        proxy.fo1();
        int i = proxy.fo2();
        System.out.println(i);

        System.in.read();
    }
}


/*
 * Decompiled with CFR.
 *
 * Could not load the following classes:
 *  com.ww.spring.aop.jdk.TestJdkProxy$Foo
 */
//package com.ww.spring.aop.jdk;
//
//import com.ww.spring.aop.jdk.TestJdkProxy;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.lang.reflect.UndeclaredThrowableException;
//
//final class $Proxy0
//        extends Proxy
//        implements TestJdkProxy.Foo {
//    private static Method m1;
//    private static Method m2;
//    private static Method m3;
//    private static Method m4;
//    private static Method m0;
//
//    public $Proxy0(InvocationHandler invocationHandler) {
//        super(invocationHandler);
//    }
//
//    static {
//        try {
//            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
//            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
//            m3 = Class.forName("com.ww.spring.aop.jdk.TestJdkProxy$Foo").getMethod("fo1", new Class[0]);
//            m4 = Class.forName("com.ww.spring.aop.jdk.TestJdkProxy$Foo").getMethod("fo2", new Class[0]);
//            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
//            return;
//        }
//        catch (NoSuchMethodException noSuchMethodException) {
//            throw new NoSuchMethodError(noSuchMethodException.getMessage());
//        }
//        catch (ClassNotFoundException classNotFoundException) {
//            throw new NoClassDefFoundError(classNotFoundException.getMessage());
//        }
//    }
//
//    public final boolean equals(Object object) {
//        try {
//            return (Boolean)this.h.invoke(this, m1, new Object[]{object});
//        }
//        catch (Error | RuntimeException throwable) {
//            throw throwable;
//        }
//        catch (Throwable throwable) {
//            throw new UndeclaredThrowableException(throwable);
//        }
//    }
//
//    public final String toString() {
//        try {
//            return (String)this.h.invoke(this, m2, null);
//        }
//        catch (Error | RuntimeException throwable) {
//            throw throwable;
//        }
//        catch (Throwable throwable) {
//            throw new UndeclaredThrowableException(throwable);
//        }
//    }
//
//    public final int hashCode() {
//        try {
//            return (Integer)this.h.invoke(this, m0, null);
//        }
//        catch (Error | RuntimeException throwable) {
//            throw throwable;
//        }
//        catch (Throwable throwable) {
//            throw new UndeclaredThrowableException(throwable);
//        }
//    }
//
//    public final void fo1() {
//        try {
//            this.h.invoke(this, m3, null);
//            return;
//        }
//        catch (Error | RuntimeException throwable) {
//            throw throwable;
//        }
//        catch (Throwable throwable) {
//            throw new UndeclaredThrowableException(throwable);
//        }
//    }
//
//    public final int fo2() {
//        try {
//            return (Integer)this.h.invoke(this, m4, null);
//        }
//        catch (Error | RuntimeException throwable) {
//            throw throwable;
//        }
//        catch (Throwable throwable) {
//            throw new UndeclaredThrowableException(throwable);
//        }
//    }
//}
