package com.ww.streamStudy.parallelStream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// * Java8 中的 Collection 接口被扩展，提供了
//         * 两个获取流的方法：
//         * 1.default Stream<E> stream() : 返回一个顺序流
//        * 2.default Stream<E> parallelStream() : 返回一个并行流
public class ParallelStreamTest {

    @Test
    public void t1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        System.out.println(list.size());
        List<Integer> streamList = new ArrayList<>();
        list.parallelStream().forEach(streamList::add);
        System.out.println(streamList.size());//数据丢失或下角标异常
    }

    @Test
    public void testStream() {
        long l = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 90000; i++) {
            list.add(i);
        }
        System.out.println(list.size());
        List<Integer> streamList = new ArrayList<>();
        //list.stream().forEach(streamList::add);
        list.forEach(streamList::add);
        System.out.println(streamList.size());
        System.out.println("时间：" + (System.currentTimeMillis() - l));
    }

//    使用resultList =new CopyOnWriteArrayList<>(); 这是个线程安全的类。
//    从源码上看，CopyOnWriteArrayList在add操作时，通过ReentrantLock进行加锁，防止并发写。
//    不给过CopyOnWriteArrayList，每次add操作都是把原数组中的元素拷贝一份到新数组中，然后在新数组中添加新元素，最后再把引用指向新数组。
//    这会导致频繁的对象创建，况且数组还是需要一块连续的内存空间，如果有大量add操作，慎用。
    @Test
    public void t2() {
        long l = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 90000; i++) {
            list.add(i);
        }
//        System.out.println(list.size());
        List<Integer> streamList = new CopyOnWriteArrayList<>();
        list.parallelStream().forEach(streamList::add);
        System.out.println(streamList.size());
        System.out.println("时间：" + (System.currentTimeMillis() - l));
    }

//    由于在并行环境中任务的执行顺序是不确定的，因此对于依赖于顺序的任务而言，并行化也许不能给出正确的结果。
    @Test
    public void t3() {
        long l = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 90000; i++) {
            list.add(i);
        }
//        System.out.println(list.size());
        List<Integer> streamList = Collections.synchronizedList(new ArrayList<>());
        list.parallelStream().forEach(streamList::add);
        System.out.println(streamList.size());
        System.out.println("时间：" + (System.currentTimeMillis() - l));
    }



}
