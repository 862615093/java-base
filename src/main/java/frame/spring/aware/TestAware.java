package frame.spring.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class TestAware {

    /**
     * Aware 接口 及 InitializingBean 接口
     *
     * 1. Aware 接口用于注入一些与容器相关信息, 例如
     *    a. BeanNameAware 注入 bean 的名字
     *    b. BeanFactoryAware 注入 BeanFactory 容器
     *    c. ApplicationContextAware 注入 ApplicationContext 容器
     *    d. EmbeddedValueResolverAware ${}
     *
     *  2、对于 b、c、d 的功能用 @Autowired 就能实现啊, 为啥还要用 Aware 接口呢
     *     简单地说:
     *          a. @Autowired 的解析需要用到 bean 后处理器, 属于扩展功能
     *          b. 而 Aware 接口属于内置功能, 不加任何扩展, Spring 就能识别
     *          c. 某些情况下, 扩展功能会失效, 而内置功能不会失效
     *
     *   3、学到了什么
     *      a. Aware 接口提供了一种【内置】 的注入手段, 可以注入 BeanFactory, ApplicationContext
     *      b. InitializingBean 接口提供了一种【内置】的初始化手段
     *      c. 内置的注入和初始化不受扩展功能的影响, 总会被执行, 因此 Spring 框架内部的类常用它们
     *
     */
    public static void main(String[] args) {

        // 干净容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 使用内置接口 BeanNameAware, ApplicationContextAware, InitializingBean
//        context.registerBean("myBean", MyBean.class);

//        context.registerBean("myConfig1", MyConfig1.class); //会失效

        context.registerBean("myConfig2", MyConfig2.class); //不会失效

        // 注册 bean & beanfactory 后置处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.registerBean(ConfigurationClassPostProcessor.class);


        context.refresh(); // 1. beanFactory 后处理器,  2. 添加 bean 后处理器, 3. 初始化单例
        context.close();

    }



}
