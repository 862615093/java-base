package juc.completableFutureStudy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 多任务组合(allOf、anyOf)
 * ①. allOf:等待所有任务完成
 * (public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs))
 * <p>
 * ②. anyOf:只要有一个任务完成
 * (public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs))
 */
public class AllOfAnyOf {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        });

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的属性");
            return "黑色+256G";
        });

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("查询商品介绍");
            return "华为";
        });
        //需要全部完成
//        futureImg.get();
//        futureAttr.get();
//        futureDesc.get();
        CompletableFuture<Void> all = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
        all.get();
//        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
//        anyOf.get();
//        System.out.println(anyOf.get());
        System.out.println("main over.....");

    }
}
