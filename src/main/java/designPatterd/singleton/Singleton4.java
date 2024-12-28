package designPatterd.singleton;

import java.io.Serializable;

/**
 * 方式四（懒汉式）：双检锁
 */
public class Singleton4 implements Serializable {
    // volatile 保证 可见性，有序性
    private static volatile Singleton4 INSTANCE = null; 
    
    //2. private保证构造器不能再其他地方被new
    private Singleton4() {
        System.out.println("private Singleton4()");
    }

    //3. 双检锁
    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton4();
                }
            }
        }
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod()");
    }
}   