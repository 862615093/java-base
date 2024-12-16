package juc.threadCommunicationStudy;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 线程通信
 */
public class threadCommunicationTest { // https://zhuanlan.zhihu.com/p/98932009

    public static void main(String[] args) {
//        m1(); ①同步
//        m2(); ②while轮询的方式
//        m3(); ③wait/notify机制
    }

    static void m3() {
        try {
            Object lock = new Object();
            ThreadA3 a = new ThreadA3(lock);
            a.setName("A");
            a.start();
            Thread.sleep(50);
            ThreadB3 b = new ThreadB3(lock);
            b.setName("B");
            b.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        线程A要等待某个条件满足时(list.size()==5)，才执行操作。线程B则向list中添加元素，改变list 的size。
//        A,B之间如何通信的呢？也就是说，线程A如何知道 list.size() 已经为5了呢？
//        这里用到了Object类的 wait() 和 notify() 方法。
//        当条件未满足时(list.size() !=5)，线程A调用wait() 放弃CPU，并进入阻塞状态。---不像②while轮询那样占用CPU
//        当条件满足时，线程B调用 notify()通知 线程A，所谓通知线程A，就是唤醒线程A，并让它进入可运行状态。
//        这种方式的一个好处就是CPU的利用率提高了。
//        但是也有一些缺点：比如，线程B先执行，一下子添加了5个元素并调用了notify()发送了通知，而此时线程A还执行；当线程A执行并调用wait()时，
//        那它永远就不可能被唤醒了。因为，线程B已经发了通知了，以后不再发通知了。这说明：通知过早，会打乱程序的执行逻辑。
    }

    static class MyList3 {
        private static List<String> list = new ArrayList<String>();
        public static void add() {
            list.add("anyString");
        }
        public static int size() {
            return list.size();
        }
    }
    static class ThreadA3 extends Thread {
        private Object lock;
        public ThreadA3(Object lock) {
            super();
            this.lock = lock;
        }
        @Override
        public void run() {
            try {
                synchronized (lock) { //同步代码块
                    if (MyList3.size() != 5) {
                        System.out.println(Thread.currentThread().getName() + " wait begin " + LocalDateTime.now());
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + " wait end " + LocalDateTime.now());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class ThreadB3 extends Thread {
        private Object lock;
        public ThreadB3(Object lock) {
            super();
            this.lock = lock;
        }
        @Override
        public void run() {
            try {
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        MyList3.add();
                        if (MyList3.size() == 5) {
                            lock.notify();
                            System.out.println(Thread.currentThread().getName() + " 已经发出了通知");
                        }
                        System.out.println(Thread.currentThread().getName() + " 添加了" + (i + 1) + "个元素!");
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static void m2() {
        MyList service = new MyList();
        ThreadA2 a = new ThreadA2(service);
        a.setName("A");
        a.start();
        ThreadB2 b = new ThreadB2(service);
        b.setName("B");
        b.start();
//        在这种方式下，线程A不断地改变条件，线程ThreadB不停地通过while语句检测这个条件(list.size()==5)是否成立 ，从而实现了线程间的通信。
//        但是这种方式会浪费CPU资源。之所以说它浪费资源，是因为JVM调度器将CPU交给线程B执行时，它没做啥“有用”的工作，只是在不断地测试 某个条件是否成立。
//        就类似于现实生活中，某个人一直看着手机屏幕是否有电话来了，而不是： 在干别的事情，当有电话来时，响铃通知TA电话来了。
    }

    static class MyList {
        private List<String> list = new ArrayList<String>();
        public void add() {
            list.add("elements");
        }
        public int size() {
            return list.size();
        }
    }

    static class ThreadA2 extends Thread {
        private MyList list;
        public ThreadA2(MyList list) {
            super();
            this.list = list;
        }
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    list.add();
                    System.out.println(Thread.currentThread().getName() +  " 添加了" + (i + 1) + "个元素");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB2 extends Thread {
        private MyList list;
        public ThreadB2(MyList list) {
            super();
            this.list = list;
        }
        @Override
        public void run() {
            try {
                while (true) {
//                    System.out.println(Thread.currentThread().getName() + " 判断size");
                    if (list.size() == 5) {
                        System.out.println(Thread.currentThread().getName() + " ==5, 线程b准备退出了");
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static void m1() {
        MyObject object = new MyObject();
        //线程A与线程B 持有的是同一个对象:object
        ThreadA3 a = new ThreadA3(object);
        ThreadB3 b = new ThreadB3(object);
        a.start();
        b.start();
//        由于线程A和线程B持有同一个MyObject类的对象object，尽管这两个线程需要调用不同的方法，但是它们是同步执行的，
//        比如：线程B需要等待线程A执行完了methodA()方法之后，它才能执行methodB()方法。这样，线程A和线程B就实现了 通信。
//        这种方式，本质上就是“共享内存”式的通信。多个线程需要访问同一个共享变量，谁拿到了锁（获得了访问权限），谁就可以执行。
    }


    static class MyObject {
        synchronized public void methodA() {
            //do something....
            System.out.println(Thread.currentThread().getName() + " methodA()方法正在执行...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized public void methodB() {
            //do some other thing
            System.out.println(Thread.currentThread().getName() + " methodB()方法正在执行...");
        }
    }

    @AllArgsConstructor
    static class ThreadA extends Thread {
        private MyObject object;

        //省略构造方法
        @Override
        public void run() {
            super.run();
            object.methodA();
        }
    }

    @AllArgsConstructor
    static class ThreadB extends Thread {
        private MyObject object;
        //省略构造方法
        @Override
        public void run() {
            super.run();
            object.methodB();
        }
    }



}
