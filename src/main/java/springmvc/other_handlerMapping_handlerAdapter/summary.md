
映射器与适配器总结

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
