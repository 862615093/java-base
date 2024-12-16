package juc.completableFutureStudy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 获得结果和触发计算(get、getNow、join、complete)
 * ①. public T get( ) 不见不散(会抛出异常) 只要调用了get( )方法,不管是否计算完成都会导致阻塞
 * <p>
 * ②. public T get(long timeout, TimeUnit unit) 过时不候
 * <p>
 * ③. public T getNow(T valuelfAbsent):没有计算完成的情况下,给我一个替代结果计算完,返回计算完成后的结果、没算完,返回设定的valuelfAbsent
 * <p>
 * ④. public T join( ):join方法和get( )方法作用一样,不同的是,join方法不抛出异常
 */
public class GetGetNowJoinComplete {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        //(1).public T get()不见不散(会抛出异常)
        //System.out.println(future.get());
        //(2).public T get(long timeout, TimeUnit unit) 过时不候2s后如果没有返回结果就报错
        //System.out.println(future.get(2,TimeUnit.SECONDS));
        //public T getNow(T valuelfAbsent)
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //没有计算完成的情况下,给我一个替代结果
        //Integer now = future.getNow(3);
        //这里停顿了3s,而我2s后就有结果了,所以可以正常拿到值 false获取到的值是1
        //如果这里停顿1s,而我2s后才有结果,那么就不可以正常拿到值,true获取到的值是444
//        boolean flag = future.complete(444);
//        System.out.println(flag + "获取到的值是" + future.get());
        System.out.println(future.join());
    }
}
