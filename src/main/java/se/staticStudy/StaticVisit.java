package se.staticStudy;

public class StaticVisit {
    public static void main(String[] args) {
        //类名.类变量名
        //说明：类变量是随着类的加载而创建，所以即使没有创建对象实例也可以访问
        System.out.println(A.name);
        A a1 = new A();
        a1.name = "haha";
        a1.num = 20;


        System.out.println("===========================================");
        A a2 = new A();
        System.out.println(a2.name);
        System.out.println(a2.num);

    }

    public void t() {

    }
}


class A {
    //类变量 : 该类所有对象共享，而实例变量每个对象独享
    //类变量的访问，必须遵守 相关的访问权限.
    public static String name = "韩顺平教育";

    //普通属性/普通成员变量/非静态属性/非静态成员变量/实例变量
    public int num = 10;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        A.name = name;
    }
}