package juc.synchronizedStudy;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  Lock8 8锁问题
 * ①. 标准访问有ab两个线程,请问先打印邮件还是短信
 *
 * ②. sendEmail方法暂停3秒钟,请问先打印邮件还是短信
 *
 * ③. 新增一个普通的hello方法,请问先打印邮件还是hello
 *
 * ④. 有两部手机,请问先打印邮件还是短信
 *
 * ⑤. 两个静态同步方法,同1部手机,请问先打印邮件还是短信
 *
 * ⑥. 两个静态同步方法, 2部手机,请问先打印邮件还是短信
 *
 * ⑦. 1个静态同步方法,1个普通同步方法,同1部手机,请问先打印邮件还是短信
 *
 * ⑧. 1个静态同步方法,1个普通同步方法,2部手机,请问先打印邮件还是短信
 */
@Slf4j
class Phone { //资源类
    public static synchronized void sendEmail() {
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("-------sendEmail");
    }

    public synchronized void sendSMS() {
        log.info("-------sendSMS");
    }

    public void hello() {
        log.info("-------hello");
    }
}

public class Lock8Demo {
    public static void main(String[] args) {//一切程序的入口,主线程
        Phone phone = new Phone();//资源类1
        Phone phone2 = new Phone();//资源类2

        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();

        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            //phone.sendSMS();
            //phone.hello();
            phone2.sendSMS();
        }, "b").start();

    }
}
/**
 * ============================================
 * 1-2
 * *  一个对象里面如果有多个synchronized方法,某一个时刻内,只要一个线程去调用其中的一个synchronized方法了,
 * *  其它的线程都只能等待,换句话说,某一个时刻内,只能有唯一的一个线程去访问这些synchronized方法
 * *  锁的是当前对象this,被锁定后,其它的线程都不能进入到当前对象的其它的synchronized方法
 * <p>
 * 3-4
 * *  加个普通方法后发现和同步锁无关
 * *  换成两个对象后,不是同一把锁了,情况立刻变化。
 * <p>
 * 5-6 都换成静态同步方法后,情况又变化
 * 三种 synchronized 锁的内容有一些差别:
 * 对于普通同步方法,锁的是当前实例对象,通常指this,具体的一部部手机,所有的普通同步方法用的都是同一把锁——实例对象本身,
 * 对于静态同步方法,锁的是当前类的Class对象,如Phone.class唯一的一个模板
 * 对于同步方法块,锁的是 synchronized 括号内的对象
 * <p>
 * 7-8
 * 当一个线程试图访问同步代码时它首先必须得到锁,退出或抛出异常时必须释放锁。
 * *
 * *  所有的普通同步方法用的都是同一把锁——实例对象本身,就是new出来的具体实例对象本身,本类this
 * *  也就是说如果一个实例对象的普通同步方法获取锁后,该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
 * *
 * *  所有的静态同步方法用的也是同一把锁——类对象本身,就是我们说过的唯一模板Class
 * *  具体实例对象this和唯一模板Class,这两把锁是两个不同的对象,所以静态同步方法与普通同步方法之间是不会有竞态条件的
 * *  但是一旦一个静态同步方法获取锁后,其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 **/



