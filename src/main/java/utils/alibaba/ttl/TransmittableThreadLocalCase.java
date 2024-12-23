package utils.alibaba.ttl;

import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示 TransmittableThreadLocal 正确使用
 * 验证结论：
 * 1、一般的线程池 父子线程不具有传递性。
 * 2、一般的线程池 子线程如果复用，子线程拥有的上下文内容会对下次使用造成“污染”，因为他放回线程池数据没有被清空。
 * 3、线程池必须使用TtlExecutors修饰，或者Runnable\Callable必须使用TtlRunnable\TtlCallable修饰子线程在执行run方法后会进行“回放”，防止污染
 */
public class TransmittableThreadLocalCase {
  

  // 如果使用一般的线程池或者Runnable\Callable时，会存在线程“污染”，比如线程池中线程会复用，复用的线程会“污染”该线程执行下一次任务
//  private static final Executor EXECUTOR = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(1000));

  // 为达到线程100%复用便于测试，线程池核心数1
  private static final Executor EXECUTOR = TtlExecutors.getTtlExecutor(new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(1000)));

  public static void main(String[] args) {
    
    RequestContext.create(new RequestContext.RequestHeader("url", "get"));
    System.out.println(Thread.currentThread().getName() + " 设置上下文内容查看：" + RequestContext.get());

    // 模拟另一个线程修改上下文内容
    EXECUTOR.execute(() -> {
      RequestContext.create(new RequestContext.RequestHeader("url", "put"));
      System.out.println(Thread.currentThread().getName() + " 另一个线程查看上下文内容：" + RequestContext.get());
    });
    
    // 保证上面 另一个线程 修改成功
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    // 异步获取上下文内容
    EXECUTOR.execute(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " 子线程(rm之前 异步)：" + RequestContext.get());
    });
    
    // 主线程修改上下文内容
    RequestContext.create(new RequestContext.RequestHeader("url", "post"));
    System.out.println(Thread.currentThread().getName() + " 修改上下文内容查看：" + RequestContext.get());
    
    // 主线程remove
    RequestContext.remove();
    System.out.println(Thread.currentThread().getName() + " 清空上下文内容查看：" + RequestContext.get());

    // 子线程获取remove后的上下文内容
    EXECUTOR.execute(() -> {
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " 子线程(rm之后 异步)：" + RequestContext.get());
    });

    System.out.println("==================main over=================");

  }
}