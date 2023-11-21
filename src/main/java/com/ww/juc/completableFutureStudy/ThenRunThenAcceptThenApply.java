package com.ww.juc.completableFutureStudy;

import java.util.concurrent.CompletableFuture;

/**
 * 对计算结果进行消费(thenRun、thenAccept、thenApply)
 * <p>
 * ①. thenRun(Runnable runnable)
 * 任务A执行完执行B,并且B不需要A的结果
 * <p>
 * ②. CompletableFuture<Void> thenAccept(Consumer<? super T> action)
 * 任务A执行完成执行B,B需要A的结果,但是任务B无返回值
 * <p>
 * ③. public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
 * 任务A执行完成执行B,B需要A的结果,同时任务B有返回值
 */
public class ThenRunThenAcceptThenApply {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenAccept(System.out::println);
        // 任务A执行完执行B,并且B不需要A的结果
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());

        // 任务A执行完成执行B,B需要A的结果,但是任务B无返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {
        }).join());

        // 任务A执行完成执行B,B需要A的结果,同时任务B有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + " resultB").join());


    }


}
