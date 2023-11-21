package com.ww.juc.completableFutureStudy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ①. CompletableFuture 提供了四个静态方法来创建一个异步操作
 * runAsync方法不支持返回值.适用于多个接口之间没有任何先后关系
 * supplyAsync可以支持返回值,我们一般用supplyAsync来创建
 * 比如在一个方法中,调用6个接口,接口A的结果需要作为接口B的入参,这个时候适合用带返回值的构造
 * //runAsync方法不支持返回值
 * public static CompletableFuture<Void> runAsync(Runnable runnable)
 * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
 * //supplyAsync可以支持返回值
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
 * ②. 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池,则使用指定的线程池运行。以下所有的方法都类同
 */
public class CompletableFutureCreate {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3));

        //(1). CompletableFuture.runAsync(Runnable runnable);
        CompletableFuture future1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "*********future1 coming in");
        });
        //这里获取到的值是null
        System.out.println(future1.get());
        //(2). CompletableFuture.runAsync(Runnable runnable,Executor executor);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //ForkJoinPool.commonPool-worker-9
            System.out.println(Thread.currentThread().getName() + "\t" + "*********future2 coming in");
        }, executor);
        //(3).public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            //pool-1-thread-1
            System.out.println(Thread.currentThread().getName() + "\t" + "future3带有返回值");
            return 1024;
        });
        System.out.println(future3.get());
        //(4).public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
        CompletableFuture<Integer> future4 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "future4带有返回值");
            return 1025;
        }, executor);
        System.out.println(future4.get());
        //关闭线程池
        executor.shutdown();
    }
}
