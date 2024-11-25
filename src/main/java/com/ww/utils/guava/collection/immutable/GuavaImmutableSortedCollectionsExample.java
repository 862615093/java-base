package com.ww.utils.guava.collection.immutable;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableSortedMap;  
import com.google.common.collect.Ordering;  
  
import java.util.Comparator;  
  
public class GuavaImmutableSortedCollectionsExample {

//    ImmutableSortedSet 和 ImmutableSortedMap
//    这两个接口分别表示不可变的排序集合和排序映射。它们提供了根据元素的自然顺序或指定的比较器排序的功能。

    public static void main(String[] args) {  
        // 创建一个根据自然顺序排序的ImmutableSortedSet  
//        ImmutableSortedSet<Integer> sortedSet =
//                ImmutableSortedSet.copyOf(Ordering.natural(), 10, 5, 15, 20);
//        System.out.println("ImmutableSortedSet (natural order): " + sortedSet);
  
        // 创建一个根据自定义比较器排序的ImmutableSortedSet  
        Comparator<String> stringComparator = Comparator.comparing(String::length);
//        ImmutableSortedSet<String> sortedSetCustomOrder =
//                ImmutableSortedSet.copyOf(stringComparator, "apple", "banana", "kiwi", "pear");
//        System.out.println("ImmutableSortedSet (custom order): " + sortedSetCustomOrder);

        // 创建一个根据自然顺序排序键的ImmutableSortedMap
        ImmutableSortedMap<Integer, String> sortedMap =
                ImmutableSortedMap.of(5, "five", 1, "one", 3, "three");
        // 注意：上面的of方法会根据键的自然顺序对条目进行排序，但这里我们传入的已经是排序好的键值对  
        System.out.println("ImmutableSortedMap (natural order): " + sortedMap);  
  
        // 创建一个根据自定义比较器排序键的ImmutableSortedMap  
        ImmutableSortedMap<String, Object> sortedMapCustomOrder =
                ImmutableSortedMap.orderedBy(stringComparator)  
                        .put("apple", 1)  
                        .put("banana", 2)  
                        .put("kiwi", 3)  
                        .put("pear", 4)  
                        .build();  
        System.out.println("ImmutableSortedMap (custom order): " + sortedMapCustomOrder);  
    }  
}