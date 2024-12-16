package frame.springmvc.controllerAdvice.exceptionHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TestExceptionHandler {

//    @SneakyThrows
//    public static void main(String[] args) {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        //异常解析器
//        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
//        resolver.setMessageConverters(Collections.singletonList(new MappingJackson2XmlHttpMessageConverter()));
//        resolver.afterPropertiesSet();
//
//        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
//        Exception exception = new Exception("e1");
//        resolver.resolveException(request, response, handlerMethod, exception); //核心
//
//        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
//    }


    /**
     * 控制器中被 @ExceptionHandler 标记的异常处理方法只会在当前控制器中生效，
     * 如果想要某个异常处理方法全局生效，则需要将异常处理方法编写在被 @ControllerAdvice 注解标记的类中。
     */
    @SneakyThrows
    public static void main(String[] args) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        ExceptionHandlerExceptionResolver resolver = context.getBean(ExceptionHandlerExceptionResolver.class);

        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        Exception exception = new Exception("e1");
        resolver.resolveException(request, response, handlerMethod, exception);//核心

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }



    static class Controller1 {
        public void foo() {
        }
    }


}


