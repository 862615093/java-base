package com.ww.collection;

import java.util.*;

public class IteratorStudy {
    public static void main(String[] args) {
        removeTest();
    }

    private static void removeTest() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(4);
        System.out.println(list);

//        1、普通for循环遍历List删除指定元素--错误！！！
//        for (int i = 0; i < list.size(); i++) {
//            //删除值==3的
//            if (list.get(i) == 3) list.remove(i);
//        }
//        System.out.println(list);

//        for循环遍历List删除元素时，让索引同步调整--正确！
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) == 3) list.remove(i--);
//        }
//        System.out.println(list);

//        foreach遍历List删除元素--错误！！！ java.util.ConcurrentModificationException
//        for (Integer i : list) {
//            if (i == 3) list.remove(i);
//        }
//        System.out.println(list);

//        迭代删除List元素--正确！java中所有的集合对象类型都实现了Iterator接口，遍历时都可以进行迭代：
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() == 3) {
                it.remove();
            }
        }
//        System.out.println(list);

//        List删除元素时，注意Integer类型和int类型的区别.
//        可以看出,List删除元素时传入数字时，默认按索引删除。
//        如果需要删除Integer对象，调用remove(object)方法，需要传入Integer类型
        list.remove(2);
        System.out.println(list);
        list.remove(new Integer(2));
        System.out.println(list);

//        总结：
//
//        1、用for循环遍历List删除元素时，需要注意索引会左移的问题。 -->  elementData[--size] = null;
//
//        2、List删除元素时，为避免陷阱，建议使用迭代器iterator的remove方式。
//        public E next() {
//            checkForComodification();
//            int i = cursor;
//            if (i >= size)
//                throw new NoSuchElementException();
//            Object[] elementData = ArrayList.this.elementData;
//            if (i >= elementData.length)
//                throw new ConcurrentModificationException();
//            cursor = i + 1;
//            return (E) elementData[lastRet = i];
//        }
//
//        3、List删除元素时，默认按索引删除，而不是对象删除。
    }
}
