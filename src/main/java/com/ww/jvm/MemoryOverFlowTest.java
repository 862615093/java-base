package com.ww.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 测试  各种情况内存溢出
 *
 * https://www.cnblogs.com/pluto-charon/p/14792515.html
 */
@RestController
public class MemoryOverFlowTest {

    static int num = 0;

    @RequestMapping("/checkHealth")
    public String checkHealth() {
        System.out.println("调用服务监测接口-----------------------");
        return "服务监测接口返回";
    }

    /**
     * 每个线程运行时所需要的内存，称为虚拟机栈
     * 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
     * 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法
     *
     * 栈溢出:递归调用一个方法，使其超过栈的最大深度   java.lang.StackOverflowError: null
     * 栈帧过多导致栈内存溢出
     * 栈帧过大导致栈内存溢出
     */
    @RequestMapping("/stackOverFlowError")
    public void stackOverFlowError() {
        System.out.println(num++);
        stackOverFlowError();
    }

    /**
     * Heap 堆
     * 通过 new 关键字，创建对象都会使用堆内存
     * 特点
     * 它是线程共享的，堆中对象都需要考虑线程安全的问题
     * 有垃圾回收机制
     *
     * 堆内存溢出:使用是循环创建对象，是堆内存溢出  -Xmx10m
     */
    @RequestMapping("/javaHeapSpace")
    public void javaHeapSpace() {
        int i = 0;
        try {
            ArrayList<String> list = new ArrayList<>();
            String str = "hello world";
            while (true) {
                list.add(str);
                str = str + str;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(i);
        }
    }

    static class OomTest{

    }

    /**
     * 方法区内存溢出, MetaSpace并不在虚拟机内存中，而是使用本地内存, MetaSpace中存储了一下信息：
     * 虚拟机加载的类信息
     * 常量池
     * 静态变量
     * 即时编译后的代码
     *
     * 元空间内存溢出: 模拟MetaSpace溢出，不断生成类往元空间放，类占据的空间会超过MetaSpace指定的大小  -XX:MaxMetaspaceSize=50m
     * java.lang.OutOfMemoryError: Metaspace
     */
    @RequestMapping("/metaSpace")
    public void metaSpace(){
        int i = 0;
        try{
            while (true){
                i++;
                /**
                 * Enhancer允许为非接口类型创建一个java代理。Enhancer动态创建了给定类型的子类但是拦截了所有的方法，
                 * 和proxy不一样的是：不管是接口还是类它都能正常工作。
                 */
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OomTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o,objects);
                    }
                });
                enhancer.create();
            }
        }catch (Throwable e){
            System.out.println("i的值为：" + i);
            e.printStackTrace();
        }
    }

}
