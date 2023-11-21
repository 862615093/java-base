package com.ww.streamStudy.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


//          lambda表达式使用前提：需要“函数式接口(只有一个抽象方法@FunctionalInterface修饰)”支持(java.util.function包下定义了丰富的Java8函数式接口)
//          lambda表达式（是函数式接口的实例也就是对象，不是函数）基本语法：Java8引入一个操作符 “->”,剪头操作符或lambda操作符
//          若只有一个参数，小括号可以省略不写, 若lambda 体中只有一条语句，return和大括号都可以省略
//          左侧：指定了 Lambda 表达式需要的所有参数
//          右侧：指定了 Lambda 体，即 Lambda 表达式要执行的功能。
//          lambda表达式有基本的五种格式，如下实例：


//        匿名内部类基本语法
//            new 类或接口(参数列表) {
//                类体
//            }


//        类型推断
//        上述 Lambda 表达式中的参数类型都是由编译器推断得出的。
//                Lambda 表达式中无需指定类型，程序依然可以编译，这是因为 javac 根据程序的上下文，在后台推断出了参数的类型。
//                Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。这就是所谓的 “类型推断"


//        函数式接口
//        1.只包含一个抽象方法的接口，称为函数式接口。
//        2.你可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda 表达式抛出一个受检异常，那么该异常需要在目标接口的抽象方法上进行声明）。
//        3.我们可以在任意函数式接口上使用 @FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口，同时 javadoc 也会包 含一条声明，说明这个接口是一个函数式接口。


//@SpringBootTest
public class LambdaTest {


    @Test
    public void t1() {
//        语法格式一：无参，无返回值，Lambda体只需一条语句
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("匿名内部类方式");
//            }
//        };
        Runnable runnable = () -> System.out.println("语法格式一：无参，无返回值");
        runnable.run();
    }

    @Test
    public void t2() {
//        语法格式二：Lambda需要一个参数, 无返回值  消费型接口
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println("匿名类部类方式，入参=" + s);
//            }
//        };
        Consumer<String> consumer = s -> System.out.println("匿名类部类方式，入参=" + s);
        consumer.accept("你好");
    }

    @Test
    public void t3() {
//        语法格式三：Lambda无参数，并且有返回值    供给型接口
//        Supplier<String> supplier = new Supplier<String>() {
//            @Override
//            public String get() {
//                return "加油啊~";
//            }
//        };
        Supplier<String> supplier = () -> "加油啊~";
        String s = supplier.get();
        System.out.println(s);
    }

    @Test
    public void t4() {
//        语法格式四：Lambda有参数， 有返回值    函数型接口
//        Function<Integer, String> function = new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) {
//                return integer.toString();
//            }
//        };
        Function<Integer, String> function = integer -> integer.toString();
        System.out.println(function.apply(100));

    }

    @Test
    public void t5() {
        //        语法格式五：Lambda 有一个参数，有一个为boolean型的返回值   断定型接口
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return false;
//            }
//        };
        Predicate<String> predicate = s -> false;
        System.out.println(predicate.test("false"));
    }


    @Test
    public void t6() {
//        作为参数传递Lambda 表达式
//        作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递，接收Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型。

//        double val = getVal(new CustomizeFunctionalInterface<Double>() {
//            @Override
//            public double get(Double aDouble) {
//                return aDouble;
//            }
//        }, 0.9);
        double val = getVal(aDouble -> aDouble, 0.9);
        System.out.println(val);


    }

    public double getVal(CustomizeFunctionalInterface<Double> customizeFunctionalInterface, double val) {
        return customizeFunctionalInterface.get(val);
    };


    @Test
    public void t7() {
//        CustomizeFunctionalInterface<Double> customizeFunctionalInterface = new CustomizeFunctionalInterface<Double>() {
//            @Override
//            public double get(Double aDouble) {
//                return aDouble;
//            }
//        };
        CustomizeFunctionalInterface<Double> customizeFunctionalInterface = aDouble -> aDouble;
        System.out.println(customizeFunctionalInterface.get(0.8));
    }
}
