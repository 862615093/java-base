package com.ww.collection.guava.multimap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;  
  
import java.util.Collection;  
import java.util.Map;  
  
public class MultimapExample {

//    Multimap 最核心的特点  就是支持一个键对应多个值。
//    这意味着我们可以向 Multimap 中添加一个键和多个值，并且可以通过键来检索到对应的值集合。
//    这种一对多的映射关系在很多场景下都非常有用，比如处理用户的多个邮箱地址、一个订单包含多个商品等。
//
//    除了支持多值映射外，Multimap 还具有以下特点：
//
//    值集合不必唯一： 与 SetMultimap 不同，普通的 Multimap 允许值重复。如果你需要值集合中的元素唯一，可以选择使用 SetMultimap。
//    顺序可保留也可不保留： Guava 提供了多种 Multimap 的实现，其中一些实现可以保留元素插入的顺序，如 LinkedHashMultimap，而另一些实现则不保证顺序，如 HashMultimap。你可以根据具体需求选择合适的实现。
//    空键和空值的支持： Multimap 允许使用 null 作为键或值，但是不同的实现可能会有不同的限制。在选择具体的 Multimap 实现时，需要注意其对空键和空值的处理方式。
//    丰富的视图： Multimap 提供了多种视图来访问和操作其中的元素。通过 asMap() 方法，你可以获取一个将键映射到对应值集合的 Map 视图；通过 entries() 方法，你可以获取一个包含所有键值对集合的视图。这些视图提供了方便的方式来遍历和操作 Multimap 中的元素。
  
    public static void main(String[] args) {  
        // 创建一个Multimap实例  
        Multimap<String, Integer> multimap = ArrayListMultimap.create();  
  
        // 使用put方法添加键值对  
        multimap.put("apple", 1);  
        multimap.put("apple", 2);  
        multimap.put("banana", 3);  
        multimap.put("orange", 4);  
        multimap.put("orange", 5);  
        multimap.put("orange", 6);

        System.out.println("multimap: " + multimap);
  
        // 使用putAll方法添加多个键值对  
//        Map<String, Integer> moreFruits = Map.of("grape", 7, "grape", 8, "kiwi", 9);
//        multimap.putAll(moreFruits);
  
        // 使用get方法获取键对应的所有值  
        System.out.println("Apples: " + multimap.get("apple")); // 输出 [1, 2]  
  
        // 使用containsKey和containsValue检查键和值是否存在  
        System.out.println("Contains key 'apple'? " + multimap.containsKey("apple")); // 输出 true  
        System.out.println("Contains value 6? " + multimap.containsValue(6)); // 输出 true  
  
        // 使用remove方法移除单个键值对  
        boolean removed = multimap.remove("banana", 3);  
        System.out.println("Removed banana=3? " + removed); // 输出 true  
  
        // 使用removeAll方法移除一个键及其所有值  
        multimap.removeAll("kiwi");  
        System.out.println("Kiwis: " + multimap.get("kiwi")); // 输出 []  
  
        // 使用keySet获取所有不同的键  
        System.out.println("Unique keys: " + multimap.keySet()); // 输出可能包含 [orange, apple, grape, banana] (注意banana和kiwi可能已被移除)  
  
        // 使用values获取所有值（包括重复值）  
        System.out.println("All values: " + multimap.values()); // 输出包含所有值的集合  
  
        // 使用entries获取所有键值对  
        System.out.println("Entries: " + multimap.entries()); // 输出所有键值对  
  
        // 使用asMap获取每个键对应值的集合的视图  
        System.out.println("As Map: " + multimap.asMap()); // 输出类似 {orange=[4, 5, 6], apple=[1, 2], grape=[7, 8]}  
  
        // 使用size方法获取键值对总数  
        System.out.println("Size of multimap: " + multimap.size()); // 输出当前multimap中键值对的总数  
  
        // 使用isEmpty检查multimap是否为空  
        System.out.println("Is multimap empty? " + multimap.isEmpty()); // 根据multimap内容输出 true 或 false  
    }  
}