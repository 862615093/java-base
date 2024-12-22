package frame.springboot.runner.run;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.util.List;

@Slf4j
public class Step1 {

    /**
     *  学到了什么：
     *    1、 run（）源码， 第一步：获取 SpringApplicationRunListeners
     *    2、 读取 spring.factories 中的配置 获取 SpringApplicationRunListeners
     *    3、 发布器发布事件顺序：
     *        发布 application starting 事件1️⃣
     *        发布 application environment 已准备事件2️⃣
     *        发布 application context 已初始化事件3️⃣
     *        发布 application prepared 事件4️⃣
     *        发布 application started 事件5️⃣
     *        发布 application ready 事件6️⃣
     *        这其中有异常，发布 application failed 事件7️⃣
     */
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication();

        // 经典的函数接口延迟调用
        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("事件监听器收到事件：" + event);
            }
        });

        System.out.println("--------------------------开始模拟----------------------------");

        // 获取事件发布器的 实现类名
        List<String> publisherClassName = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, Step1.class.getClassLoader());
        System.out.println("publisherClassName: " + publisherClassName);

        for (String name : publisherClassName) {

            // 构造器反射获取发布器
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(app, args);

            // 获取发布器后 开始模拟发布 run流程中7个事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

            // 1、spring boot 开始启动事件
            publisher.starting(bootstrapContext);

            // 2、环境信息准备完毕
            publisher.environmentPrepared(bootstrapContext, new StandardEnvironment());

            // 3、创建 spring 容器，调用初始化器之后发布此事件
            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context);

            // 4、所有 bean definition 加载完毕事件
//            publisher.contextLoaded(context);
//
//            // 5、spring 容器初始化完毕（调用 refresh() 方法后）
//            context.refresh();
//            publisher.started(context, null);
//
//            // 6、spring boot 启动完毕事件
//            publisher.ready(context, null);
//
//            // 7、启动过程中出现异常，spring boot 启动出错事件
//            publisher.failed(context, new Exception("出错了"));
        }
    }


}
