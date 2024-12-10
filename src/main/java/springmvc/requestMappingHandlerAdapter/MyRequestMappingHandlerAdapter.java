package springmvc.requestMappingHandlerAdapter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//需要在配置类中将 RequestMappingHandlerAdapter 添加到 Spring 容器，但该类中需要测试的方法被 protected 修饰，无法直接使用，因此创建一个子类，将子类添加到 Spring 容器中
public class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
        @Override
        public ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
            return super.invokeHandlerMethod(request, response, handlerMethod);
        }
    }