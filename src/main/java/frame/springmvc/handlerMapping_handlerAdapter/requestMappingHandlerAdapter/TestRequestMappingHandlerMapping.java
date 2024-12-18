package frame.springmvc.handlerMapping_handlerAdapter.requestMappingHandlerAdapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TestRequestMappingHandlerMapping {

    /**
     * 学到什么：
     * 1、RequestMappingHandlerAdapter 实现了 HandlerAdapter 接口，HandlerAdapter 用于执行控制器方法。
     */
    public static void main(String[] args) throws Exception {
        // 老规矩来个容器
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        // 解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        // mock请求
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test2");
        request.setParameter("name", "wangwei");

        // mock响应
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 执行控制器方法
        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) handlerMapping.getHandler(request).getHandler()));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 测试自定义参数解析器
        MockHttpServletRequest tokenRequest = new MockHttpServletRequest("PUT", "/test3");
        tokenRequest.addHeader("token", "token info");
        handlerAdapter.invokeHandlerMethod(tokenRequest, response, ((HandlerMethod) handlerMapping.getHandler(tokenRequest).getHandler()));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 测试自定义返回值解析器
        MockHttpServletRequest test4Req = new MockHttpServletRequest("GET", "/test4");
        handlerAdapter.invokeHandlerMethod(test4Req, response, ((HandlerMethod) handlerMapping.getHandler(test4Req).getHandler()));
        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }





}
