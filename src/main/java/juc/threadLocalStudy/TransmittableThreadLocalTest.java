package juc.threadLocalStudy;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池中如何共享数据？
 * 在真实的业务场景中，一般很少用单独的线程，绝大多数，都是用的线程池。
 * 那么，在线程池中如何共享ThreadLocal对象生成的数据呢？
 * 因为涉及到不同的线程，如果直接使用ThreadLocal，显然是不合适的。
 * <p>
 * 使用TransmittableThreadLocal，它并非JDK自带的类，而是阿里巴巴开源jar包中的类。
 */
public class TransmittableThreadLocalTest {

    public static void main(String[] args) {
//        m1();
        m2();
    }

    public static void m1() {
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set(6);
        System.out.println(Thread.currentThread().getName() + " 父线程获取数据：" + threadLocal.get());

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        threadLocal.set(6);
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 第一次从线程池中获取数据：" + threadLocal.get());
        });

        threadLocal.set(7);
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 第二次从线程池中获取数据：" + threadLocal.get());
        });

//        由于这个例子中使用了单例线程池，固定线程数是1。
//        第一次submit任务的时候，该线程池会自动创建一个线程。
//        因为使用了InheritableThreadLocal，所以创建线程时，会调用它的init方法，将父线程中的inheritableThreadLocals数据复制到子线程中。
//        所以我们看到，在主线程中将数据设置成6，第一次从线程池中获取了正确的数据6。之后，在主线程中又将数据改成7，但在第二次从线程池中获取数据却依然是6。
//        因为第二次submit任务的时候，线程池中已经有一个线程了，就直接拿过来复用，不会再重新创建线程了。
//        所以不会再调用线程的init方法，所以第二次其实没有获取到最新的数据7，还是获取的老数据6。

    }

    public static void m2() {
        TransmittableThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<>();
        threadLocal.set(6);
        System.out.println(Thread.currentThread().getName() + " 父线程获取数据：" + threadLocal.get());

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));

        threadLocal.set(6);
        ttlExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 第一次从线程池中获取数据：" + threadLocal.get());
        });

        threadLocal.set(7);
        ttlExecutorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 第二次从线程池中获取数据：" + threadLocal.get());
        });
    }
}
