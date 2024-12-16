package juc.synchronizedStudy;

/**
 * 同步机制
 */
public class SyncMechanism {

    public static void main(String[] args) {
//        同步方法:
//        synchronized同步方法控制对对象的访问，
//        每个对象对应一把锁，每个synchronized同步方法都必须获得调用该方法的锁的对象才能执行，否则线程会阻塞，方法一旦执行，就独占该锁，
//        直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行。
//        缺陷：若将一个大的方法申明为synchronized同步方法将会影响效率
//        方法里面需要修改的内容才需要锁，锁的太多，浪费资源

//        private [static] synchronized void buy() {
//            System.out.println();
//        }

//        synchronized同步方法的总结:
//        同步方法法仍然涉及到同步监视器（锁），只是不需要显式的声明
//        静态方法的同步监视器是当前类本身
//        非静态方法的同步监视器是this

//        同步代码块:
//        synchronized(Obj) {
//            需要被同步的代码块（操作共享数据的代码即为需要被同步的代码）
//        }
//        Obj称为同步监视器
//        Obj可以是任何对象，但是推荐使用共享资源作为同步监视器
//        锁的对象是变化的量，是需要增删改查的量
//        同步方法中不需要指定同步监视器，因为同步方法中的同步监视器就是this，就是这个对象本身，或者是class


    }
}
