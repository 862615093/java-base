package designPatterd.singleton;

import java.io.Serializable;

/**
 * 方式五（懒汉式）：内部类懒汉式 ： jvm在加载外部类的时候，不会加载静态内部类。避免了双检锁的缺点
 */
public class Singleton5 implements Serializable {

	//1. 内部类保证能使用外部类中的变量，而且无法让外界访问到
    private static class Holder {
        //2. 放在静态属性中初始化对象，天然保证了线程安全问题!!!
        static Singleton5 INSTANCE = new Singleton5();
    }
    
    //3. private保证构造器不能再其他地方被new
    private Singleton5() {
        System.out.println("private Singleton5()");
    }

    public static Singleton5 getInstance() {
        return Holder.INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod()");
    }
}  