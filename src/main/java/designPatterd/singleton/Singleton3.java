package designPatterd.singleton;

import java.io.Serializable;

/**
 * 方式三（懒汉式）：
 */
public class Singleton3 implements Serializable {
    
    //1. private static 保证属性私有， 全局唯一，不被继承。类加载同时生成。
    private static Singleton3 INSTANCE = null;
    
    //2. private保证构造器不能再其他地方被new
    private Singleton3() {
        System.out.println("private Singleton3()");
    }

    //3. 方法上加悲观锁，保证线程安全
    public static synchronized Singleton3 getInstance() {
        if (INSTANCE == null) {
            //4. 因为不是在静态属性中初始化的，所以需要考虑线程安全问题！！！
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod()");
    }
}