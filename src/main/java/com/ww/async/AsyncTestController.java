package com.ww.async;

import cn.hutool.extra.spring.SpringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://blog.csdn.net/weixin_40910372/article/details/103465867
 * https://www.cnblogs.com/daimzh/p/13360671.html
 */
@Slf4j
@RestController
@Api(tags = {"@Async注解失效问题的分析与解决方案"})
@RequestMapping("/async")
public class AsyncTestController {

//    总结：
//    @Async注解失败的常见场景：
//    1、没有在@SpringBootApplication启动类当中添加注解@EnableAsync注解。
//    2、异步方法使用注解@Async的返回值只能为void或者Future。
//    3、没有走Spring的代理类。
//    4、非public方法 （对于private方法，由于子类无法访问，也就无法进行代理。通过这里的方法，找到了生成的代理类字节码，发现里面也没有对父类的private字段与方法进行覆盖。）
//    5、尽量避免使用默认使用的线程池是SimpleAsyncTaskExecutor

//    @Async注解使用细节
//    @Async注解一般用在方法上，如果用在类上，那么这个类所有的方法都是异步执行的；
//    所使用的@Async注解方法的类对象应该是Spring容器管理的bean对象
//    @Async可以放在接口处（或者接口方法上）。但是只有使用的是JDK的动态代理时才有效，CGLIB会失效。因此建议：统一写在实现类的方法上
//    需要注解@EnableAsync来开启异步注解的支持
//    若你希望得到异步调用的返回值，请你的返回值用Futrue变量包装起来

    /***************************************************************************************************************/

    @GetMapping("test3")
    public void testAsync3() throws InterruptedException {
        log.info("=====主线程执行: " + Thread.currentThread().getName());
        //其实我们的注入对象都是从Spring容器中给当前Spring组件进行成员变量的赋值，
        //AsyncTestController在Spring容器中实际存在的是它的代理对象。
        AsyncTestController asyncTestController = SpringUtil.getBean(AsyncTestController.class);
        asyncTestController.doAsync01();
        asyncTestController.doAsync02();
        log.info("=====主线程执行: " + Thread.currentThread().getName());
    }

    /*****************************************************************************************************************/

    @Autowired
    private AsyncService asyncService;

    @GetMapping("test2")
    public void testAsync2() throws InterruptedException {
        log.info("=====主线程执行: " + Thread.currentThread().getName());
        asyncService.doAsync01(); //asyncService代理对象可以正常调用
        asyncService.doAsync02();
        log.info("=====主线程执行: " + Thread.currentThread().getName());
    }

    /***********************************************************************************************/

    @GetMapping("test1")
    public void testAsync1() throws InterruptedException {
        log.info("=====主线程执行: " + Thread.currentThread().getName());
        this.doAsync01();//调用方法的是对象本身而不是代理对象
        this.doAsync02();
        log.info("=====主线程执行: " + Thread.currentThread().getName());
    }

    @Async
    public void doAsync01() throws InterruptedException {
        Thread.sleep(3000);
        log.info("=====子线程执行: " + Thread.currentThread().getName());
    }

    @Async
    public void doAsync02() {
        log.info("=====子线程执行: " + Thread.currentThread().getName());
    }
}