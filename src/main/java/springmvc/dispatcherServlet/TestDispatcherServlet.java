package springmvc.dispatcherServlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

@Slf4j
public class TestDispatcherServlet {


    /**
     * 学到什么：
     * 1、Tomcat 容器初始化成功，Spring 容器初始化成功，但 DispatcherServlet 还未被初始化。
     * 2、当Tomcat 服务器 首次 使用到 DispatcherServlet 时，才会由Tomcat 服务器初始化 DispatcherServlet。
     * 3、在初始化时会从 Spring 容器中找一些 Web 需要的组件, 如 HandlerMapping、HandlerAdapter 等
     */
    public static void main(String[] args) {
        //选择支持内嵌Tomcat 服务器的 Spring 容器作为 ApplicationContext 的实现
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);


    }


}
