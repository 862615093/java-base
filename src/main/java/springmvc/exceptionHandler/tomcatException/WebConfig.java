package springmvc.exceptionHandler.tomcatException;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
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
         * 在控制器中添加请求路径为 /error 的控制器方法，该方法被 @ResponseBody 标记，最终返回 JSON 格式的数据
         */
        @RequestMapping("/error")
        @ResponseBody
        public Map<String, Object> error(HttpServletRequest request) {
            // tomcat 会将异常对象存储到 request 作用域中，可以直接获取
            Throwable e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            return Collections.singletonMap("error", e.getMessage());
        }
    }



}
