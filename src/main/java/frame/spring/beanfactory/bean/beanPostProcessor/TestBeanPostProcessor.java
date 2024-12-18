package frame.spring.beanfactory.bean.beanPostProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class TestBeanPostProcessor {


    /**
     * bean 后处理器的作用
     *
     * 常见的bean后置处理器：
     * 1、AutowiredAnnotationBeanPostProcessor ： @Autowired @Value
     * 2、CommonAnnotationBeanPostProcessor ： @Resource @PostConstruct @PreDestroy
     *
     *             学到了什么
     *                 a. @Autowired 等注解的解析属于 bean 生命周期阶段(依赖注入, 初始化)的扩展功能
     *                 b. 这些扩展功能由 bean 后处理器来完成
     *
     */
    public static void main(String[] args) {

        // 1、GenericApplicationContext 是一个【干净】的容器，不包含后置处理器
        GenericApplicationContext context = new GenericApplicationContext();

        // 2、用原始方法注册三个 bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        // 3、添加常见的bean后置处理器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); // @Autowired @Value
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());// @Value

        context.registerBean(CommonAnnotationBeanPostProcessor.class); // @Resource @PostConstruct @PreDestroy

        // 4、初始化容器
        context.refresh(); // 执行beanFactory后处理器, 添加bean后处理器, 初始化所有单例

        // bean是否注入
        System.out.println("====================================");
        System.out.println(context.getBean(Bean1.class));

        // 5、销毁容器
        context.close();


    }





}
