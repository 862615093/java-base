package com.ww.se.codeblockStudy;

public class CodeBlock01 {
    public static void main(String[] args) {
        Movie movie = new Movie("你好，李焕英");
        System.out.println("===============");
        Movie movie2 = new Movie("唐探 3", 100, "陈思诚");
    }

    //    细节：
//    代码块分为 静态 和 非静态
//    静态代码块随着类加载而加载和静态方法属性类似。 非静态代码块没创建一个对象就会执行一次
//    类什么时候加载？ 1.new 对象 2.类调用静态成员 3.子类创建对象，父类也会被加载

}

class Movie {
    private String name;
    private double price;
    private String director;

    //3 个构造器-》重载
    //老韩解读
    //(1) 下面的三个构造器都有相同的语句
    //(2) 这样代码看起来比较冗余
    //(3) 这时我们可以把相同的语句，放入到一个代码块中，即可
    //(4) 这样当我们不管调用哪个构造器，创建对象，都会先调用代码块的内容
    //(5) 代码块调用的顺序优先于构造器..
    /*static*/ {
        System.out.println("电影屏幕打开...");
        System.out.println("广告开始...");
        System.out.println("电影正是开始...");
    };

    public Movie(String name) {
        System.out.println("Movie(String name) 被调用...");
        this.name = name;
    }

    public Movie(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Movie(String name, double price, String director) {
        System.out.println("Movie(String name, double price, String director) 被调用...");
        this.name = name;
        this.price = price;
        this.director = director;
    }
}