package frame.springmvc.returnValueHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.*;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

@Slf4j
public class TestReturnValueHandler {

    /**
     *      学到了什么:
     *      a. 每个返回值处理器能干啥
     *      1) 看是否支持某种返回值
     *      2) 返回值或作为模型、或作为视图名、或作为响应体 ...
     *
     *      b. 组合模式在 Spring 中的体现 + 1
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        // 1. 测试返回值类型为 ModelAndView

        // 2. 测试返回值类型为 String 时, 把它当做视图名

        // 3. 测试返回值添加了 @ModelAttribute 注解时, 此时需找到默认视图名

        // 4. 测试返回值不加 @ModelAttribute 注解且返回非简单类型时, 此时需找到默认视图名

        // 5. 测试返回值类型为 ResponseEntity 时, 此时不走视图流程

        // 6. 测试返回值类型为 HttpHeaders 时, 此时不走视图流程

        // 7. 测试返回值添加了 @ResponseBody 注解时, 此时不走视图流程
        test7(context);
    }

    private static void test7(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test7");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); // 渲染视图
            } else {
                for (String name : response.getHeaderNames()) {
                    System.out.println(name + "=" + response.getHeader(name));
                }
                System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
        }
    }

    private static void test6(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test6");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); // 渲染视图
            } else {
                for (String name : response.getHeaderNames()) {
                    System.out.println(name + "=" + response.getHeader(name));
                }
                System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
        }
    }

    private static void test5(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test5");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); // 渲染视图
            } else {
                System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
        }
    }

    private static void test4(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test4");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test4");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        ServletWebRequest webRequest = new ServletWebRequest(request, new MockHttpServletResponse());
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            renderView(context, container, webRequest); // 渲染视图
        }
    }

    private static void test3(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test3");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test3");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        ServletWebRequest webRequest = new ServletWebRequest(request, new MockHttpServletResponse());
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            renderView(context, container, webRequest); // 渲染视图
        }
    }

    private static void test2(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test2");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            renderView(context, container, webRequest); // 渲染视图
        }
    }

    private static void test1(AnnotationConfigApplicationContext context) throws Exception {
        Method method = Controller.class.getMethod("test1");
        Controller controller = new Controller();
        Object returnValue = method.invoke(controller); // 获取返回值

        HandlerMethod methodHandle = new HandlerMethod(controller, method);

        ModelAndViewContainer container = new ModelAndViewContainer();
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
        if (composite.supportsReturnType(methodHandle.getReturnType())) { // 检查是否支持此类型的返回值
            composite.handleReturnValue(returnValue, methodHandle.getReturnType(), container, webRequest);
            System.out.println("模型<<<<<<<<<<" + container.getModel());
            System.out.println("视图名<<<<<<<<" + container.getViewName());
            System.out.println("是否跳过模型视图渲染<<<" + container.isRequestHandled());
            renderView(context, container, webRequest); // 渲染视图
        }
    }


    //提供构造 HandlerMethodReturnValueHandlerComposite 对象的方法
    public static HandlerMethodReturnValueHandlerComposite getReturnValueHandler() {
        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
        composite.addHandlers(Arrays.asList(
                new ModelAndViewMethodReturnValueHandler(),
                new ViewNameMethodReturnValueHandler(),
                new ServletModelAttributeMethodProcessor(false),
                new HttpEntityMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())),
                new HttpHeadersReturnValueHandler(),
                new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())),
                new ServletModelAttributeMethodProcessor(true)
        ));
        return composite;
    }


    //渲染视图方法
    @SuppressWarnings("all")
    private static void renderView(ApplicationContext context,
                                   ModelAndViewContainer container,
                                   ServletWebRequest webRequest) throws Exception {
        log.debug("渲染视图<<<<<<<<<<<<<");
        FreeMarkerViewResolver resolver = context.getBean(FreeMarkerViewResolver.class);
        String viewName = container.getViewName() != null ? container.getViewName() : new DefaultRequestToViewNameTranslator().getViewName(webRequest.getRequest());
        log.debug("没有获取到视图名, 采用默认视图名: {}", viewName);
        // 每次渲染时, 会产生新的视图对象, 它并非被 Spring 所管理, 但确实借助了 Spring 容器来执行初始化
        View view = resolver.resolveViewName(viewName, Locale.getDefault());
        view.render(container.getModel(), webRequest.getRequest(), webRequest.getResponse());
        System.out.println(new String(((MockHttpServletResponse) webRequest.getResponse()).getContentAsByteArray(), StandardCharsets.UTF_8));
    }


    @Slf4j
    static class Controller {

        /**
         * ModelAndView 类型的返回值由 ModelAndViewMethodReturnValueHandler 处理，构造时无需传入任何参数。
         * 解析 ModelAndView 时，将其中的视图和模型数据分别提取出来，放入 ModelAndViewContainer 中，之后根据视图信息找到对应的模板页面，再将模型数据填充到模板页面中，完成视图的渲染。
         */
        public ModelAndView test1() {
            log.debug("test1()");
            ModelAndView mav = new ModelAndView("view1");
            mav.addObject("name", "张三");
            return mav;
        }

        /**
         * 控制器方法的返回值是字符串类型时，返回的字符串即为视图的名称。与 ModelAndView 类型的返回值相比，不包含模型数据。
         * 此种类型的返回值由 ViewNameMethodReturnValueHandler 处理，构造时无需传入任何参数。
         */
        public String test2() {
            log.debug("test2()");
            return "view2";
        }

        /**
         * @ModelAttribute 注解作用在方法上时，会将方法的返回值作为模型数据添加到 ModelAndViewContainer 中。
         */
        @ModelAttribute
        public User test3() {
            log.debug("test3()");
            return new User("李四", 20);
        }

        public User test4() {
            log.debug("test4()");
            return new User("王五", 30);
        }

        /**
         * HttpEntity 类型的返回值由 HttpEntityMethodProcessor 处理，构造时需要传入一个消息转换器列表。
         * 这种类型的返回值表示响应完成，无需经过视图的解析、渲染流程再生成响应。
         */
        public HttpEntity<User> test5() {
            log.debug("test5()");
            return new HttpEntity<>(new User("赵六", 40));
        }

        /**
         *  HttpEntity 相比，HttpHeaders 只包含响应头信息，HttpHeaders 类型的返回值由 HttpHeadersReturnValueHandler 处理，构造时无需传入任何参数。
         * 与 HttpEntity 一样，这种类型的返回值也表示响应完成，无需经过视图的解析、渲染流程再生成响应
         */
        public HttpHeaders test6() {
            log.debug("test6()");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/html");
            return headers;
        }

        /**
         * @ResponseBody 标记的方法的返回值由 RequestResponseBodyMethodProcessor 处理，构造时需要传入一个消息转换器列表。
         * 这样的返回值也表示响应完成，无需经过视图的解析、渲染流程再生成响应
         */
        @ResponseBody
        public User test7() {
            log.debug("test7()");
            return new User("钱七", 50);
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class User {
        private String name;
        private int age;
    }

}
