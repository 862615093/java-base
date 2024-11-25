package com.ww.utils.guava.cache;

import com.google.common.cache.*;
import com.google.common.collect.Lists;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Guava中的缓存是本地缓存的实现，与ConcurrentMap相似，但不完全一样。
 * 最基本的区别就是，ConcurrentMap会一直保存添加进去的元素，除非你主动remove掉。而Guava Cache为了限制内存的使用，通常都会设置自动回收
 * <p>
 * Guava Cache的使用场景：
 * 以空间换取时间，就是你愿意用内存的消耗来换取读取性能的提升
 * 你已经预测到某些数据会被频繁的查询
 * 缓存中存放的数据不会超过内存空间
 */
public class CacheDemo {

    public static void main(String[] args) throws Exception {
//        cacheCreateTest();
//        loadingCacheTest();
        removeListenerTest();
    }


    //Cache是通过CacheBuilder对象来build出来的，build之前可以设置一系列的参数
    public static void cacheCreateTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100) //设置缓存最大容量
                .expireAfterWrite(1, TimeUnit.MINUTES) //过期策略，写入一分钟后过期
                .build();
        cache.put("a", "a1");
        String value = cache.getIfPresent("a");
        System.out.println(value);
    }

    //LoadingCache继承自Cache,当从缓存中读取某个key时，假如没有读取到值，LoadingCache会自动进行加载数据到缓存
    public static void loadingCacheTest() throws Exception {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .refreshAfterWrite(Duration.ofMillis(10))//10分钟后刷新缓存的数据
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        Thread.sleep(1000);
                        System.out.println(key + " load data");
                        return key + " add value";
                    }
                });
        System.out.println("------------------");
        System.out.println(loadingCache.get("a"));
        System.out.println("0000000000000000");
        System.out.println(loadingCache.get("b"));
        System.out.println("==================");
    }


    /**
     * 最大容量可以通过两种维度来设置：
     * 1、maximumSize 最大记录数，存储数据的个数
     * 2、maximumWeight 最大容量，存储数据的大小
     */
    public static void maxSizeTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(2)//缓存最大个数
                .build();
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");

        System.out.println(cache.getIfPresent("a"));
        System.out.println(cache.getIfPresent("b"));
        System.out.println(cache.getIfPresent("c"));

        Cache<String, String> cache1 = CacheBuilder.newBuilder()
                .maximumWeight(1024 * 1024 * 1024)//最大容量为1M
                //用来计算容量的Weigher
                .weigher(new Weigher<String, String>() {
                    @Override
                    public int weigh(String key, String value) {
                        return key.getBytes().length + value.getBytes().length;
                    }
                })
                .build();
        cache1.put("x", "1");
        cache1.put("y", "2");
        cache1.put("z", "3");

        System.out.println(cache1.getIfPresent("x"));
        System.out.println(cache1.getIfPresent("y"));
        System.out.println(cache1.getIfPresent("z"));

    }

    /**
     * 过期时间设置：
     * 1、expireAfterWrite 写入后多长时间，数据就过期了
     * 2、expireAfterAccess 数据多长时间没有被访问，就过期
     */
    public static void expireTest() throws InterruptedException {
//        Cache<String,String> cache = CacheBuilder.newBuilder()
//                .maximumSize(100)//缓存最大个数
//                .expireAfterWrite(5,TimeUnit.SECONDS)//写入后5分钟过期
//                .build();
//        cache.put("a","1");
//        int i = 1;
//        while(true){
//            System.out.println("第" + i + "秒获取到的数据为：" + cache.getIfPresent("a"));
//            i++;
//            Thread.sleep(1000);
//        }

        Cache<String, String> cache1 = CacheBuilder.newBuilder()
                .maximumSize(100)//缓存最大个数
                .expireAfterAccess(5, TimeUnit.SECONDS)//5秒没有被访问，就过期
                .build();
        cache1.put("a", "1");
        Thread.sleep(3000);
        System.out.println("休眠3秒后访问：" + cache1.getIfPresent("a"));
        Thread.sleep(4000);
        System.out.println("休眠4秒后访问：" + cache1.getIfPresent("a"));
        Thread.sleep(5000);
        System.out.println("休眠5秒后访问：" + cache1.getIfPresent("a"));
    }


    /**
     * 回收策略设置：
     * 1、expireAfterWrite 写入多长时间后就回收
     * 2、expireAfterAccess 多长时间没有被访问就回收
     * 3、手动回收
     */
    public static void invalidateTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.put("a", "1");
        cache.put("b", "2");

        //从缓存中清除key为a的数据
        cache.invalidate("a");
        System.out.println(cache.getIfPresent("a"));

        cache.put("x", "x1");
        cache.put("y", "y1");
        System.out.println("x清除之前：" + cache.getIfPresent("x"));
        System.out.println("y清除之前：" + cache.getIfPresent("y"));
        List<String> list = Lists.newArrayList("x", "y");

        //批量清除
        cache.invalidateAll(list);
        System.out.println("x清除之后：" + cache.getIfPresent("x"));
        System.out.println("y清除之后：" + cache.getIfPresent("y"));

        cache.put("y", "y1");
        cache.put("z", "z1");

        //清空缓存所有的数据
        cache.invalidateAll();

        System.out.println("全部清除后：" + cache.getIfPresent("y"));
        System.out.println("全部清除后：" + cache.getIfPresent("z"));
    }

    /**
     * 数据清除监听器设置
     * 可以给Cache中的对象加一个监听，当有对象被删除时会有事件通知
     */
    public static void removeListenerTest() throws InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .expireAfterWrite(Duration.ofSeconds(5))//5秒后自动过期
                //添加一个remove的监听器
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println("[" + notification.getKey() + ":" + notification.getValue() + "] 被删除了");
                    }
                })
                .build();

        cache.put("a", "1");
        Thread.sleep(2000);
        cache.put("b", "2");
        cache.put("c", "3");
        Thread.sleep(2000);
        cache.put("d", "4");
        Thread.sleep(5000);
        cache.put("e", "5");
        cache.invalidate("e");
    }


}
