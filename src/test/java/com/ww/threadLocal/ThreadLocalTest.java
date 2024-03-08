package com.ww.threadLocal;


import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * ThreadLocal并不是为了解决线程安全问题，而是提供了一种将变量绑定到当前线程的机制，类似于隔离的效果。
 * ThreadLocal跟线程安全基本不搭边：线程安全or不安全取决于绑上去的实例是怎样的：
 * 每个线程独享一份new出来的实例 -> 线程安全
 * 多个线程共享一份“引用类型”实例 -> 线程不安全
 *
 * ThreadLocal最大的用处就是用来把实例变量共享成全局变量，在程序的任何方法中都可以访问到该实例变量而已。
 *
 */
public class ThreadLocalTest {
    private static final ThreadLocal<Person> THREAD_LOCAL = new InheritableThreadLocal<>();

    @Test
    public void t() throws InterruptedException{
        setData(new Person());

        Thread subThread1 = new Thread(() -> {
            Person data = getAndPrintData();
            if (data != null)
                data.setAge(100);
            getAndPrintData(); // 再打印一次
        });
        subThread1.start();
        subThread1.join();//主线程等待子线程的终止才能往下执行


        Thread subThread2 = new Thread(() -> getAndPrintData());
        subThread2.start();
        subThread2.join();

        // 主线程获取线程绑定内容
        getAndPrintData();
        System.out.println("======== Finish =========");
    }

    private void setData(Person person) {
        System.out.println("set数据，线程名：" + Thread.currentThread().getName());
        THREAD_LOCAL.set(person);
    }

    private Person getAndPrintData() {
        // 拿到当前线程绑定的一个变量，然后做逻辑（本处只打印）
        Person person = THREAD_LOCAL.get();
        System.out.println("get数据，线程名：" + Thread.currentThread().getName() + "，数据为：" + person);
        return person;
    }

    @Setter
    @ToString
    private static class Person {
        private Integer age = 18;
    }
}
