package springmvc.exceptionHandler.tomcatException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
public class TestTomcatException {

    /**
     * 学到了什么：超出 handleMethod 范围内的异常该如何统一处理
     *
     * 可以利用 @ExceptionHandler 和 @ControllerAdvice 注解全局对控制器方法中抛出的异常进行处理，
     * 但针对诸如 filter 中不在控制器方法中的异常就变得无能为力了。
     * 因此需要一个更上层的“异常处理者”，这个“异常处理者”就是 Tomcat 服务器。
     *
     */
    public static void main(String[] args) {
        //用于 Web 应用的上下文类，它支持基于注解的 Java 配置和嵌入式 Web 服务器的启动。同时支持自动扫描和注册 Bean。
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        //输出所有的路径映射信息
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        handlerMapping.getHandlerMethods().forEach((k, v) -> System.out.println("映射路径: " + k + "\t方法信息: " + v));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//        context.close();
    }


}
