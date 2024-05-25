package com.ww.collection.guava.multimap;

import com.google.common.collect.*;
  
import java.util.*;  
  
public class SocialNetwork {

//    常用的 Multimap 实现
//    Guava 提供了多种 Multimap 的实现，每种实现都有其特定的用途和性能特点。以下是一些常用的 Multimap 实现及其适用场景：
//
//    1. ArrayListMultimap
//    如果你需要保留值的插入顺序，并且希望获得较好的迭代性能，那么 ArrayListMultimap 是一个不错的选择。
//    它基于 ArrayList 实现，提供了常数时间的 get 操作。但是需要注意的是，在某些情况下，如遍历所有键值对时，性能可能不如其他实现。
//
//    2. HashMultimap
//    如果你对值的顺序不关心，但是需要快速的键查找性能，那么 HashMultimap 是一个很好的选择。
//    它基于 HashMap 实现，提供了高效的键查找操作。但是需要注意的是，HashMultimap 不允许 null 键或值。
//
//    3. LinkedHashMultimap
//    如果你既需要快速的键查找性能，又希望保留值的插入顺序，那么 LinkedHashMultimap 是一个很好的折中选择。
//    它结合了 HashMap 和 LinkedList 的特点，既提供了快速的键查找性能，又保留了元素插入的顺序。
//
//    4. TreeMultimap
//    如果你需要按键的顺序访问 Multimap 中的元素，并且希望根据键进行排序，那么 TreeMultimap 是一个很好的选择。
//    它基于 TreeMap 实现，可以根据键的自然顺序或提供的 Comparator 对键进行排序。
  
    public static void main(String[] args) {  
        // 使用ArrayListMultimap来保存用户的朋友列表，保留插入顺序  
        ArrayListMultimap<String, String> friendsByUser = ArrayListMultimap.create();  
          
        // 使用HashMultimap来保存用户对朋友的点赞，不保证顺序  
        HashMultimap<String, String> likesByUser = HashMultimap.create();  
          
        // 使用LinkedHashMultimap来保存用户的消息，保留插入顺序  
        LinkedHashMultimap<String, String> messagesByUser = LinkedHashMultimap.create();  
          
        // 使用TreeMultimap来保存用户的活动日志，按键（时间戳）排序  
        TreeMultimap<Long, String> activityLog = TreeMultimap.create();  
  
        // 添加一些朋友  
        friendsByUser.putAll("Alice", Arrays.asList("Bob", "Charlie", "David"));  
        friendsByUser.putAll("Bob", Arrays.asList("Alice", "Charlie"));  
        friendsByUser.put("Charlie", "David");  
  
        // 添加一些点赞  
        likesByUser.put("Alice", "Bob's post");  
        likesByUser.put("Alice", "Charlie's photo");  
        likesByUser.put("Bob", "Charlie's photo");  
        likesByUser.putAll("Charlie", Arrays.asList("Alice's status", "David's video"));  
  
        // 添加一些消息  
        messagesByUser.put("Alice", "Hi Bob!");  
        messagesByUser.put("Alice", "How are you Charlie?");  
        messagesByUser.put("Bob", "Charlie, where are you?");  
        messagesByUser.putAll("Charlie", Arrays.asList("Here I am!", "Alice, check this out!"));  
  
        // 添加一些活动日志（假设时间戳是简单的顺序数字）  
        activityLog.put(1L, "Alice added a new friend");  
        activityLog.put(2L, "Bob liked a photo");  
        activityLog.put(3L, "Charlie sent a message");  
  
        // 打印出Alice的朋友列表  
        System.out.println("Alice's friends: " + friendsByUser.get("Alice"));  
  
        // 打印出点赞了Charlie的照片的用户  
        for (String user : likesByUser.keys()) {  
            if (likesByUser.containsEntry(user, "Charlie's photo")) {  
                System.out.println(user + " liked Charlie's photo");  
            }  
        }  
  
        // 打印出Alice发送的所有消息  
        for (String message : messagesByUser.get("Alice")) {  
            System.out.println("Alice said: " + message);  
        }  
  
        // 打印出按时间顺序的所有活动日志  
        for (Map.Entry<Long, String> entry : activityLog.entries()) {  
            System.out.println("Timestamp: " + entry.getKey() + ", Event: " + entry.getValue());  
        }  
  
        // 假设我们想要移除Bob作为Alice的朋友  
        friendsByUser.remove("Alice", "Bob");  
  
        // 再次打印出Alice的朋友列表，确认Bob已被移除  
        System.out.println("Alice's friends after removing Bob: " + friendsByUser.get("Alice"));  
    }  
}