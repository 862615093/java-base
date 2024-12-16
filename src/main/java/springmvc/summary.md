
一、映射器与适配器总结

HandlerMapping 用于建立请求路径与控制器之间的映射关系 (常见的几种HandlerMapping介绍)：
1、RequestMappingHandlerMapping：解析 @RequestMapping 及其派生注解，建立请求路径与控制器方法之间的映射关系。
2、BeanNameUrlHandlerMapping：与 Bean 的名称进行匹配，要求名称必须以 / 开头。
3、RouterFunctionMapping：将 RequestPredicate 映射到 HandlerFunction。
4、SimpleUrlHandlerMapping：静态资源映射。
5、WelcomePageHandlerMapping：映射 / 根路径，寻找欢迎页。
注意：映射器之间的顺序也是有要求的，SpringBoot 中的映射器按上述顺序排序。

HandlerAdapter 用于对各种处理器进行适配调用（适配器 模式）：
1、RequestMappingHandlerAdapter：执行被 @RequestMapping 标记的控制器方法，内部还会使用参数解析器、返回值处理器对控制器方法的参数、返回值进行处理（组合 模式）。
2、SimpleControllerHandlerAdapter：执行实现了 Controller 接口的处理器。
3、HandlerFunctionAdapter：处理 HandlerFunction 函数式接口。
4、HttpRequestHandlerAdapter：处理 HttpRequestHandler 接口，用于静态资源处理
5、ResourceHttpRequestHandler 中的 setResourceResolvers() 方法是 责任链 模式体现。


二、MVC 处理流程

当浏览器发送一个请求 http://localhost:8080/hello 后，请求到达服务器，其处理流程是：
（服务器提供了 DispatcherServlet，它使用的是标准 Servlet 技术具体实现）

1、路径：默认映射路径为 /，即会匹配到所有请求 URL，可作为请求的统一入口，DispatcherServlet 也被称之为 前控制器。
（但也有例外：JSP 不会匹配到 DispatcherServlet 。其它有路径的 Servlet 匹配优先级也高于 DispatcherServlet）

2、创建：在 SpringBoot 中，由自动配置类 DispatcherServletAutoConfiguration 提供 DispatcherServlet 的 Bean。

3、初始化：DispatcherServlet 初始化时会优先到容器里寻找各种组件，作为它的成员变量：
    3.1、HandlerMapping，初始化时记录映射关系
    3.2、HandlerAdapter，初始化时准备参数解析器、返回值处理器、消息转换器
    3.3、HandlerExceptionResolver，初始化时准备参数解析器、返回值处理器、消息转换器
    3.4、ViewResolver
    3.5、DispatcherServlet 利用 RequestMappingHandlerMapping 查找控制器方法
例如根据 /hello 路径找到被 @RequestMapping("/hello") 标记的控制器方法，控制器方法会被封装成 HandlerMethod 对象，并结合 匹配到的 **拦截器** 一起返回给 DispatcherServlet。
HandlerMethod 和 拦截器filter 合称为 HandlerExecutionChain（调用链）对象。

DispatcherServlet 接下来会
4、调用拦截器的 preHandle() 方法，返回一个布尔类型的值。若返回 true，则放行，进行后续调用，反之拦截请求，不进行后续调用。

5、RequestMappingHandlerAdapter 调用处理器方法，准备数据绑定工厂、模型工厂、ModelAndViewContainer、将 HandlerMethod 完善为 ServletInvocableHandlerMethod。
    @ControllerAdvice 全局增强点 1️⃣：利用 @ModelAttribute 补充模型数据
    @ControllerAdvice 全局增强点 2️⃣：利用 @InitBinder 补充自定义类型转换器
    使用 HandlerMethodArgumentResolver 准备参数
    @ControllerAdvice 全局增强点 3️⃣：利用 RequestBodyAdvice 接口对请求体增强
    调用 ServletInvocableHandlerMethod
    使用 HandlerMethodReturnValueHandler 处理返回值
    @ControllerAdvice 全局增强点 4️⃣：利用 RequestBodyAdvice 对响应体增强
    根据 ModelAndViewContainer 获取 ModelAndView
    如果返回的 ModelAndView 为 null，不走第 7 步视图解析及渲染流程。例如返回值处理器调用了 HttpMessageConverter 将结果转换为 JSON，这时 ModelAndView 就为 null
    如果返回的 ModelAndView 不为 null，会在第 7 步走视图解析及渲染流程


6、调用拦截器的 postHandle() 方法。

7、处理异常或视图渲染。
如果出现异常，使用 ExceptionHandlerExceptionResolver 处理异常流程 ，例如：@ControllerAdvice 全局增强点 5️⃣：利用 @ExceptionHandler 进行统一异常处理

8、调用拦截器的 afterCompletion() 方法。


