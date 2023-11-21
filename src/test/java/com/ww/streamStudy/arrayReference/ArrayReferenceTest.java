package com.ww.streamStudy.arrayReference;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;

/**
 * 数组引用： 格式： type[] :: new
 */
public class ArrayReferenceTest {

    @Test
    public void t1() {
//        Function<String, String[]> function = new Function<String, String[]>() {
//            @Override
//            public String[] apply(String s) {
//                String[] strings = new String[1];
//                strings[0] = s;
//                return strings;
//            }
//        };

//        Function<String, String[]> function = (e1) -> {
//            String[] strings = new String[1];
//            strings[0] = e1;
//            return strings;
//        };
//        System.out.println(Arrays.toString(function.apply("haha")));

        Function<Integer, Integer[]> function = Integer[]::new;
    }

}
