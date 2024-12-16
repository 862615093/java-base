package frame.spring.aop.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
@Slf4j
public class TestCglibProxy {

    // 需要代理的目标类
    static class Target {
        public void foo() {
            System.out.println("target foo()...");
        }
    }

    // 代理是子类型, 目标是父类型， 代理类和目标类 父子关系！
    public static void main(String[] param) {
        Target target = new Target();
        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (p, method, args, methodProxy) -> {
            System.out.println("before...");
//            Object result = method.invoke(target, args); // 用方法反射调用目标

            /**
             * 1、methodProxy 它可以避免反射调用
             * 2、当调用 MethodProxy 对象的 invoke() 方法或 invokeSuper() 方法时，就会生成这两个代理类，它们都继承至 FastClass。
             * 3、在 JDK 中需要反射调用 16 次方法后才会生成优化反射调用的代理类，而在 CGLib 中，当调用 MethodProxy.create() 方法时就会生成由于优化反射调用的代理类。
             * 4、在 JDK 中一个方法的反射调用优化就要生成一个代理类，而在 CGLib 中，一个代理类生成两个 FastClass 代理类。
             */
            Object result = methodProxy.invoke(target, args); // 内部没有用反射, 需要目标类 （spring）
//            Object result = methodProxy.invokeSuper(p, args); // 内部没有用反射, 需要代理类
            return result;
        });
        System.out.println(proxy.getClass());
        proxy.foo();
    }
}