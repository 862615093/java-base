package frame.springmvc.handlerMapping_handlerAdapter.requestMappingHandlerMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Slf4j
public class TestRequestMappingHandlerMapping {

    /**
     * 学到什么：
     * 1、HandlerMapping，即处理器映射器，用于建立请求路径与控制器方法的映射关系。
     * 2、RequestMappingHandlerMapping 是 HandlerMapping 的一种实现，根据类名可知，它是通过 @RequestMapping 注解来实现路径映射。
     * 3、当 Spring 容器中没有 HandlerMapping 的实现时，尽管 DispatcherServlet 在初始化时会添加一些默认的实现，但这些实现不会交由 Spring 管理，而是作为 DispatcherServlet 的成员变量。
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        // 解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        // 获取映射结果
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        System.out.println("========================================================");
        handlerMethods.forEach((k, v) -> System.out.println(k + " = " + v));
        // 根据给定请求获取控制器方法，返回处理器执行链
        HandlerExecutionChain chain = handlerMapping.getHandler(new MockHttpServletRequest("GET", "/test1"));
        System.out.println(chain);
        System.out.println("========================================================");
    }
}
