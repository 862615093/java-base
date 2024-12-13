package springmvc.exceptionHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class TestExceptionHandler {


    /**
     * 学到了什么 ： 说白了不会返回正常结果，而是自己通过@ExceptionHandler修饰的方法，返回的结果！！！
     *
     *     a. ExceptionHandlerExceptionResolver 能够重用参数解析器、返回值处理器，实现组件重用
     *     b. 能够支持嵌套异常
     */
    @SneakyThrows
    public static void main(String[] args) {

        // 准备异常解析器
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionResolver.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));

        // 调用该方法，添加默认的参数解析器和返回值处理器
        exceptionResolver.afterPropertiesSet();

        // 准备http的 request、response、handleMethod、exception
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        Exception e = new ArithmeticException("除以零");

        // 异常处理核心方法！！！
        exceptionResolver.resolveException(request, response, handlerMethod, e);

        //测试异常处理方法被 @ResponseBody 注解标记
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//        handlerMethod = new HandlerMethod(new Controller2(), Controller2.class.getMethod("foo"));
//        ModelAndView modelAndView = exceptionResolver.resolveException(request, response, handlerMethod, e);
//        System.out.println(modelAndView.getModel());
//        System.out.println(modelAndView.getViewName());
//
//        // 嵌套异常
//        handlerMethod = new HandlerMethod(new Controller3(), Controller3.class.getMethod("foo"));
//        e = new Exception("e1", new RuntimeException("e2", new IOException("e3")));
//        exceptionResolver.resolveException(request, response, handlerMethod, e);
//        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
//
//        // 异常处理方法参数处理
//        handlerMethod = new HandlerMethod(new Controller4(), Controller4.class.getMethod("foo"));
//        e = new Exception("e4");
//        exceptionResolver.resolveException(request, response, handlerMethod, e);
//        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }





    static class Controller1 {
        public void foo() {
        }

        @ResponseBody
        @ExceptionHandler // 底层 通过 @ExceptionHandler 找到需要异常处理的方法，用map存 key: ArithmeticException val:handle()方法，最后反射调用该方法，将结果返回
        public Map<String, Object> handle(ArithmeticException e) {
            return Collections.singletonMap("error", e.getMessage());
        }
    }

    static class Controller2 {
        public void foo() {
        }

        @ExceptionHandler
        public ModelAndView handler(ArithmeticException e) {
            return new ModelAndView("test2", Collections.singletonMap("error", e.getMessage()));
        }
    }

    static class Controller3 {
        public void foo() {
        }

        @ResponseBody
        @ExceptionHandler
        public Map<String, Object> handle(IOException e) {
            return Collections.singletonMap("error", e.getMessage());
        }
    }

    static class Controller4 {
        public void foo() {}

        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle(Exception e, HttpServletRequest request) {
            System.out.println(request);
            return Collections.singletonMap("error", e.getMessage());
        }
    }



}
