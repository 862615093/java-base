package com.ww.se.staticStudy;


//        原文链接：https://blog.csdn.net/weixin_44325444/article/details/109273226

//        首先static的成员是在类加载的时候初始化的，JVM的CLASSLOADER的加载，首次主动使用加载，而非static的成员是在创建对象的时候，即new 操作的时候才初始化的；
//        先后顺序是先加载，才能初始化，那么加载的时候初始化static的成员，此时非static的成员还没有被加载必然不能使用，而非static的成员是在类加载之后，
//        通过new操作符创建对象的时候初始化，此时static 已经分配内存空间，所以可以访问！
//        简单点说：静态成员属于类,不需要生成对象就存在了.而非静态需要生成对象才产生.所以静态成员不能直接访问非静态.

//下面说说静态的特点：
//        1.随着类的加载而加载
//
//        也就是，说静态会随着类的消失而消失，说明静态的生命周期最长
//
//        2.优先于对象的存在
//
//        明确一点：静态是先存在的对象是后存在的
//
//        3.被所有对象共享
//
//        4.可以直接被类名多调用


public class StaticMethodDetail {
    public static void main(String[] args) {
        D.hi();//ok
        //非静态方法，不能通过类名调用
        //D.say();, 错误，需要先创建对象，再调用
        new D().say();//可以
    }
}

class D {
    private int n1 = 100;
    private static int n2 = 200;

    public void say() {//非静态方法,普通方法
    }

    //静态方法,类方法
    public static void hi() {
        //类方法中不允许使用和对象有关的关键字，
        //比如 this 和 super。普通方法(成员方法)可以。
        //System.out.println(this.n1);
    }

    //类方法(静态方法)中 只能访问 静态变量 或静态方法
    //口诀:静态方法只能访问静态成员.
    //在一个类的静态成员中去访问非静态成员之所以会出错。 因为在类的非静态成员不存在的时候静态成员就已经存在了，访问一个内存中不存在的东西当然会出错。
    public static void hello() {
        System.out.println(n2);
        System.out.println(D.n2);
        //System.out.println(this.n2);不能使用
        hi();//OK
        //say();//错误
    }

    //普通成员方法，既可以访问 非静态成员，也可以访问静态成员
    //小结: 非静态方法可以访问 静态成员和非静态成员
    public void ok() {
        //非静态成员
        System.out.println(n1);
        say();
        //静态成员
        System.out.println(n2);
        hello();
    }
}