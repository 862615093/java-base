package com.ww.streamStudy.constructorReferences;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * 构造器引用
 * 格式： ClassName::new
 * 与函数式接口相结合，自动与函数式接口中方法兼容。
 * 可以把构造器引用赋值给定义的方法，与构造器参数列表要与接口中抽象方法的参数列表一致！
 *
 */
public class ConstructorReferencesTest {

    @Test
    public void t1() {
//        Function<Integer, Integer> function = new Function<Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer) {
//                return 100;
//            }
//        };
        Function<Integer, Integer> function = Integer::new;
        System.out.println(function.apply(100));
    }




}
