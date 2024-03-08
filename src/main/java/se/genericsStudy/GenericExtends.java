package se.genericsStudy;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型的继承和通配符
 */
public class GenericExtends {
    public static void main(String[] args) {
        Object o = new String("xx");
        //泛型没有继承性
        //List<Object> list = new ArrayList<String>();

        //举例说明下面三个方法的使用
        List<Object> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<AA1> list3 = new ArrayList<>();
        List<BB1> list4 = new ArrayList<>();
        List<CC1> list5 = new ArrayList<>();
        //如果是 List<?> c ，可以接受任意的泛型类型
        printCollection1(list1);
        printCollection1(list2);
        printCollection1(list3);
        printCollection1(list4);
        printCollection1(list5);

        //List<? extends AA1> c： 表示 上限，可以接受 AA1 或者 AA1 子类
        // printCollection2(list1);//×
        // printCollection2(list2);//×
        printCollection2(list3);//√
        printCollection2(list4);//√
        printCollection2(list5);//√

        //List<? super AA1> c: 支持 AA1 类以及 AA1 类的父类，不限于直接父类
        printCollection3(list1);//√
        //printCollection3(list2);//×
        printCollection3(list3);//√
        //printCollection3(list4);//×
        //printCollection3(list5);//×
    }

    // ? extends AA1 表示 上限，可以接受 AA1 或者 AA1 子类
    public static void printCollection2(List<? extends AA1> c) {
        for (Object object : c) {
            System.out.println(object);
        }
    }

    //说明: List<?> 表示 任意的泛型类型都可以接受
    public static void printCollection1(List<?> c) {
        for (Object object : c) { // 通配符，取出时，就是 Object
            System.out.println(object);
        }
    }

    // ? super 子类类名 AA1:支持 AA1 类以及 AA1 类的父类，不限于直接父类，
    //规定了泛型的下限
    public static void printCollection3(List<? super AA1> c) {
        for (Object object : c) {
            System.out.println(object);
        }
    }
}

class AA1 {
}

class BB1 extends AA1 {
}

class CC1 extends BB1 {
}