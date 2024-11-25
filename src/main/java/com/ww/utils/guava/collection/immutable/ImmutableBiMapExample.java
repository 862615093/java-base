package com.ww.utils.guava.collection.immutable;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;  
  
public class ImmutableBiMapExample {

//    ImmutableBiMap
//    表示不可变的、双向映射的集合。它同时提供了键到值和值到键的映射关系，并且保证了键和值的唯一性。
//    与 ImmutableMap 类似，它也不允许添加、删除或更改映射关系
  
    public static void main(String[] args) {  
        // 使用 Builder 创建 ImmutableBiMap  
        Builder<String, Integer> builder = ImmutableBiMap.builder();  
        builder.put("one", 1);  
        builder.put("two", 2);  
        builder.put("three", 3);  
        ImmutableBiMap<String, Integer> biMap = builder.build();  
  
        // 输出整个双向映射  
        System.out.println("ImmutableBiMap: " + biMap);  
  
        // 通过键获取值  
        Integer valueOne = biMap.get("one");  
        System.out.println("Value for 'one': " + valueOne);  
  
        // 通过值获取键（这是 BiMap 的特点）  
        String keyForValueTwo = biMap.inverse().get(2);  
        System.out.println("Key for value 2: " + keyForValueTwo);  
  
        // 尝试修改（注意：这会失败，因为 ImmutableBiMap 是不可变的）  
        // biMap.put("four", 4); // 这行代码会导致编译错误  
  
        // 尝试使用已存在的值作为键进行插入（也会失败，因为值也必须唯一）  
        // builder.put("four", 2); // 这同样会导致错误，即使你试图在 build() 之后再做  
    }  
}