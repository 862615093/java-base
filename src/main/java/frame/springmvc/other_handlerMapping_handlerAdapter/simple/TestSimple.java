package frame.springmvc.other_handlerMapping_handlerAdapter.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

@Slf4j
public class TestSimple {

    /**
     * 学到什么：mvc 流程实现的其他方案
     * <p>
     * BeanNameUrlHandlerMapping 与 RequestMappingHandlerMapping 类似，也是用于解析请求路径，
     * 只不过 BeanNameUrlHandlerMapping 将根据请求路径在 Spring 容器中寻找同名的 Bean，对请求进行处理，这个 Bean 必须 以 / 开头。比如：请求路径为 /c1，寻找的 Bean 的名称也是 /c1。
     * <p>
     * SimpleControllerHandlerAdapter 与 RequestMappingHandlerAdapter 也类似，也是用于调用控制器方法，
     * 但要求控制器类必须实现 org.springframework.web.servlet.mvc.Controller 接口。
     */
    public static void main(String[] args) {

        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

    }


}
