package juc.createThreadStudy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThreadMethod {

    public static void main(String[] args)/* throws ExecutionException, InterruptedException*/ {
//        MyThread t1 = new MyThread();
//        t1.start();

//        MyRunnable thread = new MyRunnable();
//        Thread t2 = new Thread(thread);
//        t2.start();

        //1.创建Callable接口实现类的对象
//        MyCallable mc = new MyCallable();
//        //2.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
//        FutureTask<Integer> ft = new FutureTask<>(mc);
//        //3.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
//        new Thread(ft).start();
//        System.out.println(ft.get());

        System.out.println("main 执行完...");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " MyThread run()方法正在执行...");
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            int i = 1 / 0;
            System.out.println(Thread.currentThread().getName() + " MyRunnable run()方法执行中...");
        }

    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println(Thread.currentThread().getName() + " MyCallable call()方法执行中...");
            return 1;
        }
    }
}



