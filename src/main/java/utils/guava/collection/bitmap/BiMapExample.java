package utils.guava.collection.bitmap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;  
  
import java.util.Set;  
  
public class BiMapExample {

//    一、BiMap简介
//    BiMap，全称Bidirectional Map，即双向映射，是一种特殊的数据结构，它可以同时支持根据键查找值和根据值查找键的操作，
//    这意味着在BiMap中，不仅键是唯一的，值也必须是唯一的。BiMap接口扩展了Map接口，并添加了一些方法来提供反向视图。
//
//    二、常用的BiMap实现类
//    Guava提供了HashBiMap和EnumBiMap两种常用的BiMap实现。
//
//    HashBiMap
//    HashBiMap是基于哈希表的双向映射实现。它提供了常数时间的containsKey、get和put操作（假设哈希函数是完美的）。由于其基于哈希表，它不保证元素的顺序。

//    EnumBiMap
//    EnumBiMap是一种特殊的BiMap，它要求键和值都是枚举类型。这种实现类型安全和高效，适用于键和值都是已知枚举值的情况。

//    三、BiMap的常用方法
//    除了继承自Map接口的方法外，BiMap还添加了一些特有的方法：
//
//    inverse(): 返回一个视图，其中的键和值与原BiMap中的值和键相反。注意，返回的是视图，对返回映射的更改将反映在原映射上，反之亦然。
//    forcePut(K key, V value): 类似于put方法，但如果键或值已经存在，则会抛出IllegalArtgumenException。
//    containsValue(Object value): 检查BiMap中是否包含指定的值。
  
    public static void main(String[] args) {  
        // 创建一个空的HashBiMap  
        BiMap<String, Integer> biMap = HashBiMap.create();  
  
        // 向BiMap中添加元素  
        biMap.put("One", 1);  
        biMap.put("Two", 2);  
        biMap.put("Three", 3);

        System.out.println("biMap: " + biMap);

        // 使用get方法通过键获取值  
        System.out.println("Two maps to: " + biMap.get("Two")); // 输出: Two maps to: 2  
  
        // 使用get方法通过值获取键（使用inverse()方法）  
        System.out.println("2 maps to: " + biMap.inverse().get(2)); // 输出: 2 maps to: Two  
  
        // 检查BiMap中是否包含某个键  
        System.out.println("Does the map contain key 'One'? " + biMap.containsKey("One")); // 输出: Does the map contain key 'One'? true  
  
        // 检查BiMap中是否包含某个值  
        System.out.println("Does the map contain value 2? " + biMap.containsValue(2)); // 输出: Does the map contain value 2? true  
  
        // 获取BiMap的键集  
        Set<String> keys = biMap.keySet();  
        System.out.println("Keys in the map: " + keys); // 输出: Keys in the map: [One, Two, Three]  
  
        // 获取BiMap的值集  
        Set<Integer> values = biMap.values();  
        System.out.println("Values in the map: " + values); // 输出: Values in the map: [1, 2, 3]  
  
        // 获取BiMap的大小  
        int size = biMap.size();  
        System.out.println("Size of the map: " + size); // 输出: Size of the map: 3  
  
        // 尝试添加一个已存在的键（这将抛出IllegalArgumentException）  
        try {  
            biMap.put("One", 4);  
        } catch (IllegalArgumentException e) {  
            System.out.println("Caught exception: " + e.getMessage()); // 输出: Caught exception: value already present: One  
        }  
  
        // 尝试添加一个已存在的值（这也将抛出IllegalArgumentException）  
        try {  
            biMap.put("Four", 1);  
        } catch (IllegalArgumentException e) {  
            System.out.println("Caught exception: " + e.getMessage()); // 输出: Caught exception: value already present: 1  
        }  
  
        // 使用forcePut方法替换现有键的值（不推荐，因为这会破坏BiMap的双向性）  
        // 注意：通常不建议使用forcePut，因为它可能会使BiMap处于不一致的状态  
        // biMap.forcePut("One", 4); // 这行代码被注释掉了，因为不推荐使用  
  
        // 从BiMap中移除一个键值对  
        Integer removedValue = biMap.remove("Two");  
        System.out.println("Removed value for key 'Two': " + removedValue); // 输出: Removed value for key 'Two': 2  
  
        // 清除整个BiMap  
        biMap.clear();  
        System.out.println("Is the map empty after clearing? " + biMap.isEmpty()); // 输出: Is the map empty after clearing? true  
    }  
}