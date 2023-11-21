package com.ww.streamStudy.methodReferences;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * 方法引用
 * 当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！
 * （实现抽象方法的参数列表，必须与方法引用方法的参数列表保持一致！）
 * 方法引用：使用操作符 “::” 将方法名和对象或类的名字分隔开来。
 * 如下三种主要使用情况：
 * 对象::实例方法
 * 类::静态方法
 * 类::实例方法 (注意：当需要引用方法的第一个参数是调用对象，并且第二个参数是需要引用方法的第二个参数(或无参数)时：ClassName::methodName)
 */
public class MethodReferencesTest {

    @Test
    public void t1() {
        PrintStream out = System.out;
        //lambda表达式：
        Consumer<String> consumer = e -> out.println(e);
        consumer.accept("gg");
        //方法引用---> 对象：：实例方法名
        Consumer<String> consumer1 = out::println;
        consumer1.accept("hh");
    }

    @Test //语法格式2
    public void t2() {
        //lambda表达式
        Comparator<Integer> comparator = (e1, e2) -> Integer.compare(e1, e2);
        int compare = comparator.compare(11, 22);
        System.out.println(compare);
        //方法引用---> 类：：静态方法名
        Comparator<Integer> comparator1 = Integer::compare;
        int compare1 = comparator1.compare(11, 22);
        System.out.println(compare1);
    }

    @Test //语法格式3
    public void t3() {
        //lambda表达式
        BiPredicate<String, String> biPredicate = (e1, e2) -> e1.equals(e2);
        boolean test = biPredicate.test("qq", "qq");
        System.out.println(test);
        //方法引用---> 类：：实例方法名
        BiPredicate<String, String> biPredicate1 = String::equals;
        boolean test1 = biPredicate1.test("qq", "ll");
        System.out.println(test1);
    }
}
