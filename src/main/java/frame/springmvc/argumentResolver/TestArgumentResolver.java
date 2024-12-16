package frame.springmvc.argumentResolver;

import cn.hutool.core.text.AntPathMatcher;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class TestArgumentResolver {


//    public static void main(String[] args) throws Exception {
//        // 控制器方法封装成 HandlerMethod
//        Method method = Controller.class.getMethod("test", String.class, String.class,
//                int.class, String.class, MultipartFile.class,
//                int.class, String.class, String.class,
//                String.class, HttpServletRequest.class, User.class,
//                User.class, User.class);
//        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), method);
//
//        // 解析每个参数值
//        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
//            String annotations = Arrays.stream(parameter.getParameterAnnotations())
//                    .map(i -> i.annotationType().getSimpleName()).collect(Collectors.joining());
//            String appendAt = !annotations.isEmpty() ? "@" + annotations + " " : "";
//            // 设置参数名解析器
//            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
//            System.out.println("[" + parameter.getParameterIndex() + "] " + appendAt +  parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName());
//        }
//    }

    /**
     * 学到了什么
     *     a. 每个参数处理器能干啥
     *         1) 看是否支持某种参数
     *         2) 获取参数的值
     *     b. 组合模式在 Spring 中的体现
     *     c. @RequestParam, @CookieValue 等注解中的参数名、默认值, 都可以写成活的, 即从 ${ } #{ }中获取
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        HttpServletRequest request = mockRequest();

        // 控制器方法封装成 HandlerMethod
        Method method = Controller.class.getMethod("test", String.class, String.class,
                int.class, String.class, MultipartFile.class,
                int.class, String.class, String.class,
                String.class, HttpServletRequest.class, User.class,
                User.class, User.class);
        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), method);

        // 准备对象绑定与类型转换
        ServletRequestDataBinderFactory binderFactory = new ServletRequestDataBinderFactory(null, null);

        // 准备 ModelAndViewContainer 用来存储中间的 Model 结果
        ModelAndViewContainer container = new ModelAndViewContainer();

        // 解析每个参数值
        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
//            // useDefaultResolution 为 false 表示必须添加 @RequestParam 注解
//            RequestParamMethodArgumentResolver resolver = new RequestParamMethodArgumentResolver(beanFactory, true);

            // 多个解析器组合
            HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
            composite.addResolvers(
                    //                                          false 表示必须有 @RequestParam
                    new RequestParamMethodArgumentResolver(beanFactory, false),
                    new PathVariableMethodArgumentResolver(),
                    new RequestHeaderMethodArgumentResolver(beanFactory),
                    new ServletCookieValueMethodArgumentResolver(beanFactory),
                    new ExpressionValueMethodArgumentResolver(beanFactory),
                    new ServletRequestMethodArgumentResolver(),
                    new ServletModelAttributeMethodProcessor(false), // 必须有 @ModelAttribute
                    new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter())),
                    new ServletModelAttributeMethodProcessor(true), // 省略了 @ModelAttribute
                    new RequestParamMethodArgumentResolver(beanFactory, true) // 省略 @RequestParam
            );

            String annotations = Arrays.stream(parameter.getParameterAnnotations())
                    .map(i -> i.annotationType().getSimpleName()).collect(Collectors.joining());
            String appendAt = !annotations.isEmpty() ? "@" + annotations + " " : "";
            // 设置参数名解析器
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            String paramInfo = "[" + parameter.getParameterIndex() + "] " + appendAt + parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName();

            if (composite.supportsParameter(parameter)) {
                Object v = composite.resolveArgument(parameter, container, new ServletWebRequest(request), binderFactory);
                System.out.println(Objects.requireNonNull(v).getClass());
                System.out.println(paramInfo + " -> " + v);
            } else {
                System.out.println(paramInfo);
            }
        }
    }





    static class Controller {
        public void test(
                @RequestParam("name1") String name1, // name1=张三
                String name2, // name2=李四
                @RequestParam("age") int age, // age=18
                @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1, // spring 获取数据
                @RequestParam("file") MultipartFile file, // 上传文件
                @PathVariable("id") int id, //  /test/124   /test/{id}
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2, // spring 获取数据  ${} #{}
                HttpServletRequest request, // request, response, session ...
                @ModelAttribute("abc") User user1, // name=zhang&age=18
                User user2, // name=zhang&age=18
                @RequestBody User user3 // json
        ) {
        }
    }

    @Getter
    @Setter
    @ToString
    static class User {
        private String name;
        private int age;
    }

    static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> map = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/123");
        System.out.println(map);
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, map);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "张三");
        request.setParameter("age", "18");
        request.setContent("hello".getBytes(StandardCharsets.UTF_8));
        return new StandardServletMultipartResolver().resolveMultipart(request);
    }

}




