package frame.springmvc.other_handlerMapping_handlerAdapter.statics;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Configuration
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory(8080);
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
    }

    /**
     *  用于静态资源映射
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(ApplicationContext context) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        // 设置静态资源处理器，得到所有映射关系
        Map<String, ResourceHttpRequestHandler> map = context.getBeansOfType(ResourceHttpRequestHandler.class);
        mapping.setUrlMap(map);
        return mapping;
    }

    /**
     * 用于静态资源处理器
     */
    @Bean
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
        return new HttpRequestHandlerAdapter();
    }

    /**
     * 添加的两个 ResourceHttpRequestHandler 类型的 Bean，分别设置了它们处理 ClassPath 路径下哪个目录下的静态资源
     */
    @Bean("/**")
    public ResourceHttpRequestHandler handler1() {
        //ResourceHttpRequestHandler 用于对静态资源进行处理，但静态资源解析的功能是由 ResourceResolver 完成的。
        //ResourceHttpRequestHandler 实现了 InitializingBean 接口，查看重写的 afterPropertiesSet()
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        // 以 / 结尾表示目录，否则认为是文件
        handler.setLocations(Collections.singletonList(new ClassPathResource("static/")));
        return handler;
    }

    @Bean("/img/**")
    public ResourceHttpRequestHandler handler2() {
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        handler.setLocations(Collections.singletonList(new ClassPathResource("images/")));
        return handler;
    }

//    @Bean("/**")
//    public ResourceHttpRequestHandler handler3() {
//        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
//        // 以 / 结尾表示目录，否则认为是文件
//        handler.setLocations(Collections.singletonList(new ClassPathResource("static/")));
//        // 不使用默认的资源解析器，而是使用自行添加的
//        handler.setResourceResolvers(Arrays.asList(
//                // 读取资源时使用缓存
//                new CachingResourceResolver(new ConcurrentMapCache("cache1")),
//                // 读取压缩资源
//                new EncodedResourceResolver(),
//                // 最基本的：从磁盘上读取静态资源
//                new PathResourceResolver()
//        ));
//        return handler;
//    }



}
