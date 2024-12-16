package frame.spring.scope;

import frame.spring.scope.sub.E;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
//@SpringBootApplication
@ComponentScan("com.ww.spring.scope.sub")
public class TestScope {

    /**
     *
     * Scope 用于指定 Bean 的作用范围，有如下五个取值：
     * singleton：单例（默认值）。容器启动时创建（未设置延迟），容器关闭时销毁
     * prototype：多例。每次使用时创建，不会自动销毁，需要调用 DefaultListableBeanFactory#destroyBean() 进行销毁
     * request：作用于 Web 应用的请求范围。每次请求用到此 Bean 时创建，请求结束时销毁
     * session：作用于 Web 应用的会话范围。每个会话用到此 Bean 时创建，会话结束时销毁
     * application：作用于 Web 应用的 ServletContext。Web 容器用到此 Bean 时创建，容器关闭时销毁
     *
     *
     * jdk >= 9 如果反射调用 jdk 中方法  运行时请添加 --add-opens java.base/java.lang=ALL-UNNAMED
     * jdk <= 8 不会有问题
     *
     */

    public static void main(String[] args) {

        /**
         *
         * 打开不同的浏览器, 刷新 http://localhost:8080/test 即可查看效果
         *
         * 学到了什么
         *    a. 有几种 scope
         *    b. 在 singleton 中使用其它几种 scope 的方法
         *    c. 其它 scope 的销毁
         *       1. 可以将通过 server.servlet.session.timeout=10s 观察 session bean 的销毁
         *       2. ServletContextScope 销毁机制疑似实现有误
         */
//        SpringApplication.run(TestScope.class, args);


        /**
         *   a. 单例注入其它 scope 的四种解决方法
         *   b. 解决方法虽然不同, 但理念上殊途同归: 都是推迟其它 scope bean 的获取
         *
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestScope.class);

        E e = context.getBean(E.class);
        log.debug("{}", e.getF1().getClass());
        log.debug("{}", e.getF1());
        log.debug("{}", e.getF1());
        log.debug("{}", e.getF1());

        log.debug("{}", e.getF2().getClass());
        log.debug("{}", e.getF2());
        log.debug("{}", e.getF2());
        log.debug("{}", e.getF2());

        log.debug("{}", e.getF3());
        log.debug("{}", e.getF3());

        log.debug("{}", e.getF4());
        log.debug("{}", e.getF4());

        context.close();


    }


}
