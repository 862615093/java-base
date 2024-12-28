package designPatterd.singleton;

import java.io.Serializable;

/**
 * 方式一 （饿汉式）： 经典方式
 */
public class Singleton1 implements Serializable {
    //1. private static final 保证属性私有， 全局唯一，不被继承。类加载同时生成。
    private static final Singleton1 INSTANCE = new Singleton1();
    
    //2. private保证构造器不能再其他地方被new
    private Singleton1() {
        //3. 构造方法抛出异常是防止反射破坏单例
        if (INSTANCE != null) {
            throw new RuntimeException("单例对象不能重复创建");
        }
        System.out.println("private Singleton1()");
    }

    //4. 提供方法供外界使用
    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod()");
    }

    //5. 是防止反序列化破坏单例
    public Object readResolve() {
        return INSTANCE;
    }
}