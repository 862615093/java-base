package com.ww.juc.interruptStudy;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class InterruptDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("-----isInterrupted() = true,程序结束。");
                    break;
                }
                try {
//                    线程调用interrupt()时
//                    (①. 如果线程处于正常活动状态,那么会将线程的中断标志设置位true,仅此而已。
//                    被设置中断标志的线程将继续正常运行,不受影响。所以,interrupt( )并不能真正的中断线程,需要被调用的线程自己进行配合才行
//                    ②. 如果线程处于被阻塞状态(例如处于sleep、wait、join等状态),在别的线程中调用当前线程对象的interrupt方法,那么线程立即被阻塞状态,并抛出一个InterruptedException异常)
//                    中断只是一种协同机制,修改中断标识位仅此而已,不是立即stop打断
//                    sleep方法抛出InterruptedException后,中断标识也被清空置为false,我们在catch没有通过调用th.interrupt( )方法再次将中断标识位设置位true,这就是导致无限循环了
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //线程的中断标志位重新设置为false,无法停下,需要再次掉interrupt()设置true
//                    Thread.currentThread().interrupt();//???????
                    e.printStackTrace();
                }
                System.out.println("------hello Interrupt");
            }
        }, "t1");
        t1.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        //修改t1线程的中断标志位为true
        new Thread(t1::interrupt,"t2").start();


//        Thread t1 = new Thread(() -> {
//            while (true) {
//                if (Thread.currentThread().isInterrupted()) {
//                    log.info("-----isInterrupted() = true,程序结束。");
//                    break;
//                }
//                log.info("------hello Interrupt");
//            }
//        }, "t1");
//        t1.start();
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //修改t1线程的中断标志位为true
//        new Thread(t1::interrupt, "t2").start();


//        Thread t1 = new Thread(() -> {
//            log.info("------hello t1");
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("------over t1");
//        }, "t1");
////        t1.setDaemon(true);  Main线程结束，其他线程也可以立刻结束，当且仅当这些子线程都是守护线程。
//        t1.start();
//
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("------over main");
    }
}
