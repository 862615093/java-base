package springmvc.other_handlerMapping_handlerAdapter.webFlux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

@Slf4j
public class TestWebFlux {

    /**
     * 学到什么：
     *
     * RouterFunctionMapping 是 Spring 5 引入的一项新功能，属于 Spring WebFlux 模块的一部分。
     * 它允许使用 函数式编程 的风格来配置 Web 路由，而不是传统的基于注解的方式。
     * 通过 RouterFunctionMapping，你可以使用 Java 函数和 Lambda 表达式来定义请求的路由和处理逻辑。
     *
     * 在 Spring 5 及更高版本中，RouterFunctionMapping 和 RouterFunction 是用于定义 Web 路由的主要组件，它们是响应式编程（Reactive Programming）模型的一部分，主要用于 Spring WebFlux。
     *
     * RouterFunctionMapping 和 RouterFunction 的概念
     * 1、RouterFunction：定义了一个路由函数，可以将 HTTP 请求映射到特定的处理函数。每个路由函数会根据请求的方法和路径来选择一个处理函数。
     * 2、RouterFunctionMapping：是 Spring WebFlux 中的路由映射组件，它将一个或多个 RouterFunction 映射到相应的 HTTP 请求上。
     *
     * 如何使用 RouterFunctionMapping
     * RouterFunctionMapping 配置方法和传统的基于注解的方式不同，它通过函数式风格来定义路由。
     * 基本的使用方式是通过 RouterFunction 创建一组路由，并将这些路由注册到 Spring 容器中。
     * 你可以通过 @Bean 注解将 RouterFunction 配置为 Spring 的 Bean。
     *
     */
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

}
