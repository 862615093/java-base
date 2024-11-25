package com.ww.utils.guava.collection.immutable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableMultimap;  
import com.google.common.collect.Multiset;  
import com.google.common.collect.Multimap;  
  
import java.util.Arrays;  
  
public class GuavaImmutableMultiCollectionsExample {

//    ImmutableMultiset 和 ImmutableMultimap
//    这两个接口分别表示不可变的多重集和多重映射。多重集允许元素重复出现，而多重映射则允许一个键映射到多个值。

//    请注意，ImmutableMultiset 和 ImmutableMultimap 都是不可变的，这意味着一旦创建，您就不能向它们添加或删除元素。
//    如果您需要一个可变的版本，可以使用 Multiset 和 Multimap 接口的其他实现，例如 HashMultiset 和 ArrayListMultimap，
//    然后在需要的时候使用 ImmutableMultiset.copyOf(multiset) 或 ImmutableMultimap.copyOf(multimap) 来创建不可变副本。
  
    public static void main(String[] args) {  
        // 创建一个ImmutableMultiset  
        ImmutableMultiset<String> multiset = ImmutableMultiset.<String>builder()  
                .addAll(Arrays.asList("apple", "apple", "banana", "orange"))  
                .addCopies("banana", 2) // 添加额外的"banana"元素  
                .build();  
        System.out.println("ImmutableMultiset: " + multiset);  
  
        // 展示元素及其出现次数  
        for (Multiset.Entry<String> entry : multiset.entrySet()) {  
            System.out.println(entry.getElement() + ": " + entry.getCount());  
        }  
  
        // 创建一个ImmutableMultimap  
        ImmutableMultimap<String, Integer> multimap = ImmutableMultimap.<String, Integer>builder()  
                .putAll("fruits", Arrays.asList(1, 2, 3))  
                .putAll("veggies", Arrays.asList(4, 5))  
                .put("fruits", 6) // 添加额外的键值对到"fruits"  
                .build();  
        System.out.println("ImmutableMultimap: " + multimap);  
  
        // 展示键及其对应的值  
        for (String key : multimap.keySet()) {  
            System.out.println(key + " => " + multimap.get(key));  
        }  
    }  
}