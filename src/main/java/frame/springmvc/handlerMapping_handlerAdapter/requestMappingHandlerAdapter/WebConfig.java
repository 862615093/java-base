package frame.springmvc.handlerMapping_handlerAdapter.requestMappingHandlerAdapter;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Configuration
@ComponentScan
public class WebConfig {
    /**
     * 内嵌 Web 容器工厂
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    /**
     * 创建 DispatcherServlet
     */
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    /**
     * 注册 DispatcherServlet，Spring MVC 的入口
     */
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    /**
     * 将 RequestMappingHandlerMapping 添加到 Spring 容器
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 将 RequestMappingHandlerAdapter 添加到 Spring 容器
     */
    @Bean
    public MyRequestMappingHandlerAdapter myRequestMappingHandlerAdapter() {
        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        MyRequestMappingHandlerAdapter adapter = new MyRequestMappingHandlerAdapter();
        adapter.setCustomArgumentResolvers(List.of(tokenArgumentResolver)); // 加入参数解析器
        YmlReturnValueHandler ymlReturnValueHandler = new YmlReturnValueHandler();
        adapter.setCustomReturnValueHandlers(List.of(ymlReturnValueHandler)); // 加入返回值解析器
        return adapter;
    }

}