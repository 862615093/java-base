package springmvc.exceptionHandler.tomcatException;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@Configuration
public class WebConfig {

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        // 解析 @RequestMapping
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        // 注意默认的 RequestMappingHandlerAdapter 不会带 jackson 转换器
        handlerAdapter.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return handlerAdapter;
    }


    /**
     * 修改了 Tomcat 服务器默认错误地址
     */
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        /*
         * ErrorPageRegistrar 由 SpringBoot 提供，TomcatServletWebServerFactory 也实现了该接口
         * 出现错误，会使用请求转发 forward 跳转到 error 地址
         */
        return webServerFactory -> webServerFactory.addErrorPages(new ErrorPage("/error"));
    }

    @Bean
    public ErrorPageRegistrarBeanPostProcessor errorPageRegistrarBeanPostProcessor() {
        /*
         * 在 TomcatServletWebServerFactory 初始化完成前，获取容器中所有的 ErrorPageRegistrar
         * 并将这些 ErrorPageRegistrar 进行注册
         */
        return new ErrorPageRegistrarBeanPostProcessor();
    }

    @Controller
    public static class MyController {

        @RequestMapping("test")
        public ModelAndView test() {
            int i = 1 / 0;
            return null;
        }

        /**
         * spring自带功能： 在控制器中添加请求路径为 /error 的控制器方法，该方法被 @ResponseBody 标记，最终返回 JSON 格式的数据
         */
//        @RequestMapping("/error")
//        @ResponseBody
//        public Map<String, Object> error(HttpServletRequest request) {
//            // tomcat 会将异常对象存储到 request 作用域中，可以直接获取
//            Throwable e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
//            return Collections.singletonMap("error", e.getMessage());
//        }

    }


    /**
     * SpringBoot提供功能： BasicErrorController 是由 SpringBoot 提供的类，它也是一个控制器
     * 向容器中添加 BasicErrorController
     */
//    @Bean
//    public BasicErrorController basicErrorController() {
//        /**
//         * errorAttributes：错误属性，可以理解成封装的错误信息对象
//         * errorProperties：也可以翻译成错误属性，用于对输出的错误信息进行配置
//         */
//        return new BasicErrorController(new DefaultErrorAttributes(), new ErrorProperties());
//    }


    @Bean
    public BasicErrorController basicErrorController() {
        ErrorProperties errorProperties = new ErrorProperties();
        errorProperties.setIncludeException(true);
        return new BasicErrorController(new DefaultErrorAttributes(), errorProperties);
    }

    /**
     * 在没有添加新的错误视图的情况下，尝试寻找视图名称为 error 的视图。
     * 这里既没有添加新的错误视图，也没有名称为 error 的视图，因此最终又会交由 Tomcat 进行处理。
     * 尝试向 Spring 容器中添加一个 View 视图，Bean 的名字 必须 是 error。
     */
    @Bean
    public View error() {
        return (model, request, response) -> {
            System.out.println(model);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<h3>服务器内部错误</h3>");
        };
    }

    /**
     * 为了能够在查找指定名称的视图时按照 View 类型的 Bean 的名称进行匹配，还需要添加一个解析器
     */
    @Bean
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }



}
