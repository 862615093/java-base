package designPatterd.singleton;

/**
 * 方式二 （饿汉式）：枚举饿汉式 优点：枚举饿汉式能 天然防止反射、反序列化破坏单例
 */
public enum Singleton2 { //1. 枚举类自带final，不允许被继承， 且不允许被new ，因为构造器默认访问权限为private
    //1. 常量对象，底层自带 public static final
    INSTANCE; 
    
    //2. 自带private保证构造器不能再其他地方被new --> 可省略不写
    private Singleton2() {
        System.out.println("private Singleton2()");
    }

    //重写Enum父类打印方法
    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    //3. 提供方法供外界使用
    public static Singleton2 getInstance() {
        return INSTANCE;
    }

    public static void otherMethod() {
        System.out.println("otherMethod()");
    }
}