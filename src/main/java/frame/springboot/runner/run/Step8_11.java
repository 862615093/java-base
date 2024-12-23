package frame.springboot.runner.run;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class Step8_11 {

    /**
     * 第八到十一步：完成 Spring 容器的创建
     *
     * 第八步：创建容器。在构造 SpringApplication 时已经推断出应用的类型，使用应用类型直接创建即可。
     * 第九步：准备容器。回调在构造 SpringApplication 时添加的初始化器。
     * 第十步：加载 Bean 定义 （3种方式）。从配置类、XML 配置文件读取 BeanDefinition、扫描某一包路径下的 BeanDefinition。
     * 第十一步：调用 ApplicationContext 的 refresh() 方法，完成 Spring 容器的创建。
     */
    @SneakyThrows
    @SuppressWarnings("all")
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication();
        app.addInitializers(applicationContext -> System.out.println("执行初始化器增强..."));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 8. 创建容器");
        GenericApplicationContext context = createApplicationContext(WebApplicationType.SERVLET);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 9. 准备容器, 执行一些增强逻辑");
        for (ApplicationContextInitializer initializer : app.getInitializers()) {
            initializer.initialize(context);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 10. 加载 Bean 定义");
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader1 = new AnnotatedBeanDefinitionReader(beanFactory);
        XmlBeanDefinitionReader reader2 = new XmlBeanDefinitionReader(beanFactory);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);

        reader1.register(Config.class);
        reader2.loadBeanDefinitions(new ClassPathResource("b03.xml"));
        scanner.scan("frame.springboot.runner.run.sub");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 11. refresh 容器");
        context.refresh();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 查看容器中的bean");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("name: " + name + " 来源: " + beanFactory.getBeanDefinition(name).getResourceDescription());
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 查看容器中的bean");

        context.close();
    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context = null;
        switch (type) {
            case SERVLET:
                context = new AnnotationConfigServletWebServerApplicationContext();
                break;
            case REACTIVE:
                context = new AnnotationConfigReactiveWebServerApplicationContext();
                break;
            case NONE:
                context = new AnnotationConfigApplicationContext();
                break;
        }
        return context;
    }



    @Configuration
    static class Config {
        @Bean
        public Bean5 bean5() {
            return new Bean5();
        }

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }
    }

    static class Bean4 {

    }

    static class Bean5 {

    }


}
