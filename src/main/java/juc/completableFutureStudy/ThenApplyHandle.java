package juc.completableFutureStudy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 对计算结果进行处理(thenApply、handle)
 * ①. public <U> CompletableFuture<U> thenApply
 * 计算结果存在依赖关系,这两个线程串行化
 * 由于存在依赖关系(当前步错,不走下一步),当前步骤有异常的话就叫停
 * <p>
 * ②. public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn):
 * 有异常也可以往下一步走,根据带的异常参数可以进一步处理
 * <p>
 * ③. whenComplete:是执行当前任务的线程执行继续执行whenComplete的任务
 * <p>
 * ④. whenCompleteAsync:是执行把whenCompleteAsync这个任务继续提交给线程池来进行执行
 */
public class ThenApplyHandle {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            return 1;
        }).thenApply(s -> {
            System.out.println(Thread.currentThread().getName() +  "-----1");
            //如果加上int error=1/0; 由于存在依赖关系(当前步错,不走下一步),当前步骤有异常的话就叫停
            //int error=1/0;
            return s + 1;
        }).thenApply(s -> {
            System.out.println(Thread.currentThread().getName() +  "-----2");
            return s + 2;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println(Thread.currentThread().getName() +  "-----result-----" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });


        System.out.println(Thread.currentThread().getName() + "\t" + "over....");

        System.out.println(future.join());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
