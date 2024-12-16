package juc.threadLocalStudy;

/**
 * 父子线程如何共享数据？
 * <p>
 * 使用InheritableThreadLocal，它是JDK自带的类，继承了ThreadLocal类。
 */
public class InheritableThreadLocalTest { //https://www.zhihu.com/question/21709953

    public static void main(String[] args) throws InterruptedException {
        m1();
        Thread.sleep(50);
        System.out.println("=========================================");
        m2();
    }

    public static void m1() {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(6);
        System.out.println("父线程获取数据：" + threadLocal.get());

        new Thread(() -> {
            System.out.println("子线程获取数据：" + threadLocal.get());
        }).start();
    }

    public static void m2() {
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set(6);
        System.out.println("父线程获取数据：" + threadLocal.get());

        new Thread(() -> {
            System.out.println("子线程获取数据：" + threadLocal.get());
        }).start();
    }

}
