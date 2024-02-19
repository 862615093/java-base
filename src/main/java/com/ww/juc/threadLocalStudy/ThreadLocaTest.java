package com.ww.juc.threadLocalStudy;

public class ThreadLocaTest {
    private static ThreadLocal<String> local = new ThreadLocal<String>();

    static void print(String str) {
        System.out.println(str + " :" + local.get()); //打印当前线程中本地内存中变量的值
//        local.remove(); //清除内存中的本地变量
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                ThreadLocaTest.local.set("xdclass_A");
                print("A");
//                System.out.println("清除后：" + local.get());
            }
        }, "A").start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            public void run() {
                System.out.println("设置前 " + local.get());
                ThreadLocaTest.local.set("xdclass_B");
                print("B");
                System.out.println("清除后 " + local.get());
            }
        }, "B").start();
    }
}