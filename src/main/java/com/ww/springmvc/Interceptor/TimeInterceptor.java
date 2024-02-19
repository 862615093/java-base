//package com.ww.springmvc.Interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//@Slf4j
//// spring 提供的类，默认被spring管理
//@Component
//public class TimeInterceptor implements HandlerInterceptor {
//
//    // 控制器(controller)方法处理前调用
//    //预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器返回值：
//    // true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
//    // 此时我们需要通过response来产生响应
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        log.info("@@@@ interceptor preHandle");
//        long start = System.currentTimeMillis();
//        // 为了在方法间传递信息，类似可用ThreadLocal
//        httpServletRequest.setAttribute("start", start);
//        // 返回false将不继续执行下面方法
//        return true;
//    }
//
//    // 控制器(controller)方法处理后调用
//    // 但是，如果controler执行期间出现异常，postHandle将不被调用
//    //后处理回调方法，实现处理器的后处理（但在渲染视图之前），
//    //此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        log.info("@@@@ interceptor postHandle");
//        long start = (long) httpServletRequest.getAttribute("start");
//        log.info("@@@@ 用时:{}", System.currentTimeMillis() - start);
//    }
//
//    // postHandle方法处理后调用
//    // 无论，controler执行期间是否出现异常，afterCompletion都将被调用
//    //整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，
//    // 类似于try-catch-finally中的finally，但仅调用处理器执行链中preHandle返回true的拦截器才会执行afterCompletion
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        log.info("@@@@ interceptor afterCompletion");
//        long start = (long) httpServletRequest.getAttribute("start");
//        log.info("@@@@ 用时:{}", System.currentTimeMillis() - start);
//        log.info("@@@@ exception:{}", e);
//    }
//}
//
