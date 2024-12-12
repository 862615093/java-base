package springmvc.controllerAdvice.modelAttribute;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class TestModelAttribute {

    /**
     * 学到什么：
     * 1、@ModelAttribute 可以作用在参数上和方法上。
     * 2、当其作用在参数上时，会将请求中的参数信息 按名称 注入到指定对象中，并将这个对象信息自动添加到 ModelMap 中。当未指定 @ModelAttribute 的 value 时，添加到 ModelMap 中的 key 是对象类型首字母小写对应的字符串。此时的 @ModelAttribute 注解由 ServletModelAttributeMethodProcessor 解析。
     * 3、当其作用在方法上时：
     *  1）如果该方法在被 @Controller 注解标记的类中，会在当前控制器中每个控制器方法执行前执行被 @ModelAttribute 标记的方法，如果该方法有返回值，自动将返回值添加到 ModelMap 中。
     *  2）当未指定 @ModelAttribute 的 value 时，添加到 ModelMap 中的 key 是返回值类型首字母小写对应的字符串。如果该方法在被 @ControllerAdvice 注解标记的类中，会在所有控制器方法执行前执行该方法。
     *  3）作用在方法上的 @ModelAttribute 注解由 RequestMappingHandlerAdapter 解析。
     *
     */
    public static void main(String[] args) throws Exception {
        t2();
    }


    static void t2() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setApplicationContext(context);
        adapter.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "张三");
        /*
            现在可以通过 ServletInvocableHandlerMethod 把这些整合在一起, 并完成控制器方法的调用, 如下
         */
        ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(new WebConfig.Controller1(), WebConfig.Controller1.class.getMethod("foo", WebConfig.User.class));

        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, null);

        handlerMethod.setDataBinderFactory(factory);
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));

        ModelAndViewContainer container = new ModelAndViewContainer();

        // 获取模型工厂方法
        Method getModelFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
        getModelFactory.setAccessible(true);
        ModelFactory modelFactory = (ModelFactory) getModelFactory.invoke(adapter, handlerMethod, factory);

        // 初始化模型数据
        modelFactory.initModel(new ServletWebRequest(request), container, handlerMethod);

        handlerMethod.invokeAndHandle(new ServletWebRequest(request), container);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(container.getModel());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        context.close();
    }

    static void t1() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "wangwei");

        ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(new WebConfig.Controller1(), WebConfig.Controller1.class.getMethod("foo", WebConfig.User.class));
        ServletRequestDataBinderFactory binderFactory = new ServletRequestDataBinderFactory(null, null);

        handlerMethod.setDataBinderFactory(binderFactory);
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());

        // getArgumentResolvers() 组装了一系列参数解析器，省略具体实现
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));

        // 暂不考虑返回值的处理
        ModelAndViewContainer container = new ModelAndViewContainer();

        // 请求中的参数信息往 ModelAndViewContainer 容器里的 ModelMap extends LinkedHashMap<String, Object> 属性中塞
        handlerMethod.invokeAndHandle(new ServletWebRequest(request), container);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(container.getModel());//{user=WebConfig.User(name=wangwei), org.springframework.validation.BindingResult.user=org.springframework.validation.BeanPropertyBindingResult: 0 errors}
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        context.close();
    }


    public static HandlerMethodArgumentResolverComposite getArgumentResolvers(AnnotationConfigApplicationContext context) {
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        composite.addResolvers(new RequestParamMethodArgumentResolver(context.getDefaultListableBeanFactory(), false), new PathVariableMethodArgumentResolver(), new RequestHeaderMethodArgumentResolver(context.getDefaultListableBeanFactory()), new ServletCookieValueMethodArgumentResolver(context.getDefaultListableBeanFactory()), new ExpressionValueMethodArgumentResolver(context.getDefaultListableBeanFactory()), new ServletRequestMethodArgumentResolver(), new ServletModelAttributeMethodProcessor(false), new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter())), new ServletModelAttributeMethodProcessor(true), new RequestParamMethodArgumentResolver(context.getDefaultListableBeanFactory(), true));
        return composite;
    }


}
