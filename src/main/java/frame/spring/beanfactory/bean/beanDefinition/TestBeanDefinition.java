package frame.spring.beanfactory.bean.beanDefinition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

@Slf4j
public class TestBeanDefinition {


    /**
     * BeanDefinition 也是一个接口，它封装了 Bean 的定义，Spring 根据 Bean 的定义，就能创建出符合要求的 Bean。
     *
     * 获取、读取 BeanDefinition 可以通过下列常用几种类完成：
     *   1、BeanDefinitionReader
     *   2、ClassPathBeanDefinitionScanner
     *   3、AnnotatedBeanDefinitionReader （尽管它并不是 BeanDefinition 的子类，但它们俩长得很像，根据其类注释可知：它能够通过编程的方式对 Bean 进行注册，是 ClassPathBeanDefinitionScanner 的替代方案，能读取通过注解定义的 Bean）
     *
     * BeanDefinitionReader 接口:
     *   作用：该接口中对 loadBeanDefinitions() 方法进行了多种重载，支持传入一个或多个 Resource 对象、资源位置来加载 BeanDefinition。
     *   1） XmlBeanDefinitionReader：通过读取 XML 文件来加载。
     *   2） PropertiesBeanDefinitionReader：通过读取 properties 文件来加载，此类已经被 @Deprecated 注解标记。
     *
     * ClassPathBeanDefinitionScanner 类：
     *   作用：通过扫描指定包路径下的 @Component 及其派生注解来注册 Bean，是 @ComponentScan 注解的底层实现。
     *        比如 MyBatis 通过继承 ClassPathBeanDefinitionScanner 实现通过 @MapperScan 注解来扫描指定包下的 Mapper 接口。
     *
     *
     * BeanDefinitionRegistry 接口：
     *   作用：AnnotatedBeanDefinitionReader 和 ClassPathBeanDefinitionScanner 中都有一个 BeanDefinitionRegistry 类型的成员变量，它是一个接口，提供了 BeanDefinition 的增加、删除和查找功能。
     *
     */
    public static void main(String[] args) {
//        AnnotatedBeanDefinitionReader
//        ClassPathBeanDefinitionScanner
//        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader();
//        beanDefinitionReader.loadBeanDefinitions()

        /******************************* 演示： 注册与获取 Bean *****************************************************/

        // 既实现了 BeanFactory，又实现了 BeanDefinitionRegistry
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // ClassPathBeanDefinitionScanner 的一种替代，编程式显式注册 bean
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.registerBean(MyBean.class);

        MyBean bean = beanFactory.getBean(MyBean.class);
        System.out.println(bean);
    }


    static class MyBean {
    }


}
