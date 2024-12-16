package utils.guava.collection.immutable;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;  
  
import java.util.Map;  
  
public class GuavaImmutableTableExample {

//    ImmutableTable 是 Google Guava 库中提供的一种不可变的三维表格数据结构。
//    它类似于 ImmutableMap，但是它可以存储两个键和一个值的映射关系，可以看作是一种特殊的集合。它允许你通过行和列来访问元素。
  
    public static void main(String[] args) {  
        // 创建一个ImmutableTable  
        ImmutableTable<String, String, Integer> table = ImmutableTable.<String, String, Integer>builder()  
                .put("apple", "red", 1)  
                .put("apple", "green", 2)  
                .put("banana", "yellow", 3)  
                .build();  
  
        // 输出整个表格  
        System.out.println("ImmutableTable: " + table);  
  
        // 获取某个键对应的行映射  
        Map<String, Integer> appleRow = table.row("apple");  
        System.out.println("Apple row: " + appleRow);  
  
        // 获取某个列对应的映射  
        Map<String, Integer> redColumn = table.column("red");  
        System.out.println("Red column: " + redColumn);  
  
        // 获取某个具体的值  
        Integer valueOfAppleRed = table.get("apple", "red");  
        System.out.println("Value of apple red: " + valueOfAppleRed);  
  
        // 判断是否包含某个键值对  
        boolean containsAppleGreen = table.contains("apple", "green");  
        System.out.println("Contains apple green: " + containsAppleGreen);  
  
        // 尝试修改（注意：这会失败，因为ImmutableTable是不可变的）  
        // table.put("apple", "red", 42); // 这行代码会导致编译错误  
    }  
}