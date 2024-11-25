package com.ww.utils.guava.collection.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;

public class GuavaImmutableCollectionsExample {

//    不可变集合：守护数据的永恒之石
//    首先，我们要介绍的是Guava提供的不可变集合。
//    在编程中，有时我们需要创建一些一旦初始化就不会再更改的集合。这些集合被称为不可变集合。
//    Guava为我们提供了ImmutableList、ImmutableSet和ImmutableMap等不可变集合的实现。
//    这些集合在创建时确定了内容，并且保证了之后无法修改。这种不可变性带来了诸多好处，比如线程安全、减少错误和提高代码可读性。
//    当你需要一个不会变动的集合时，Guava的不可变集合将是你的最佳选择。

//    1. ImmutableList
//    一个不可变的列表实现，提供了与Java List接口类似的方法，但保证了列表内容不可更改。
//
//    2. ImmutableSet
//    一个不可变的集合实现，与Java Set接口类似，但不允许添加或删除元素。
//
//    3. ImmutableMap
//    一个不可变的映射实现，类似于Java的Map接口，但键值对是固定的，无法修改。
//
//    这些不可变集合在创建时确定内容，之后不可更改，有助于编写线程安全的代码。

    public static void main(String[] args) {

        // 创建一个ImmutableList  
        ImmutableList<String> immutableList = ImmutableList.of("Apple", "Banana", "Cherry");
        System.out.println("ImmutableList: " + immutableList);

        // 尝试修改ImmutableList（这将导致编译时错误）  
        // immutableList.add("Date"); // 这行代码将无法编译

        // 创建一个ImmutableSet  
        ImmutableSet<Integer> immutableSet = ImmutableSet.of(1, 2, 3, 4, 5);
        System.out.println("ImmutableSet: " + immutableSet);

        // 尝试修改ImmutableSet（这也将导致编译时错误）  
        // immutableSet.add(6); // 这行代码也无法编译  

        // 创建一个ImmutableMap  
        ImmutableMap<String, Integer> immutableMap = ImmutableMap.of("One", 1, "Two", 2, "Three", 3);
        System.out.println("ImmutableMap: " + immutableMap);

        // 尝试修改ImmutableMap（这同样会导致编译时错误）  
        // immutableMap.put("Four", 4); // 这行代码也无法编译  

        // 访问ImmutableMap中的元素  
        Integer value = immutableMap.get("Two");
        System.out.println("Value associated with 'Two': " + value);
    }
}