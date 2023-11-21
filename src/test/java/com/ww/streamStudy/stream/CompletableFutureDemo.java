package com.ww.streamStudy.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    //https://blog.csdn.net/qq_31635851/article/details/117002455


    @Test
    public void supplyAsyncMethod() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello World!";
        });
        System.out.println("======================");
        System.out.println(cf.get());//等待线程结果 阻塞
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    public void supplyAsyncMethod2() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Hello World!";
        }, executorService);
        System.out.println(Thread.currentThread().getName());
        System.out.println(cf.get());
    }

    // thenApply() 通过传递阶段结果来执行一个函数。
    // 当我们将supplyAsync()与thenApply()一起使用时，那么thenApply()将结果作为从supplyAsync()获得的参数传递来执行给定的函数
//    @Test
//    public void thenApply() throws Exception {
//        CompletableFuture<String> cf =
//                CompletableFuture.supplyAsync(()-> getDataById(10))
//                .thenApply(data -> sendData(data));
//
//        System.out.println(cf.get());
//    }
//
//    private static String getDataById(int id) {
//        System.out.println("getDataById: "+ Thread.currentThread().getName());
//        return "Data123:"+ id;
//    }
//    private static String sendData(String data) {
//        System.out.println("sendData: "+ Thread.currentThread().getName());
//        System.out.println(data);
//        return data;
//    }


    //    在完成给定操作后，whenComplete() 返回具有相同结果或异常的新CompletionStage。
//    行为是BiConsumer，其中第一个值是CompletionStage的结果，第二个是错误（如果有的话），否则为null。
//    @Test
//    public void whenComplete() throws Exception {
//        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> getDataById(10))
//                .whenComplete((data, error) -> {
//                    consumeData(data);
//                    if (error != null) {
//                        System.out.println(error);
//                    }
//                });
//        cf.get();
//    }
//
//    private static String getDataById(int id) {
//        System.out.println("getDataById: " + Thread.currentThread().getName());
//        int i = 1 / 0;
//        return "Data:" + id;
//    }
//
//    private static void consumeData(String data) {
//        System.out.println("consumeData: " + Thread.currentThread().getName());
//        System.out.println(data);
//    }

    @Test
    public void streamTest() {
        List<Integer> list = Arrays.asList(10, 20, 30);
        long count = list.stream().map(n-> CompletableFuture.supplyAsync(()-> getDataById(n)))
                .map(cf -> cf.thenApply(data -> sendData(data)))
                .map(t->t.join()).count();

        System.out.println("Number of elements:"+ count);
    }

    private static String getDataById(int id) {
        System.out.println("getDataById: "+ Thread.currentThread().getName());
        return "Data:"+ id;
    }
    private static String sendData(String data) {
        System.out.println("sendData: "+ Thread.currentThread().getName());
        System.out.println(data);
        return data;
    }


}
