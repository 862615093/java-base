//package com.ww.frame.springmvc.Interceptor;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.ArrayList;
//
///**
// * 把第三方的filter加入到spring的容器中
// */
//@Configuration
//public class WebConfigFilter {
//
//    /**
//     * 使用 FilterRegistrationBean 而不是直接 @Component
//     * 一个Filter的好处是：可以通过setUrlPatterns，指定哪些路径通过拦截器，哪些路径不通过
//     */
//    @Bean
//    @SuppressWarnings("all")
//    public FilterRegistrationBean timeFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        Filter filter = new TimeFilter();
//        registrationBean.setFilter(filter);
//        ArrayList<String> urls = new ArrayList<>();
////        urls.add("/filter");
////        urls.add("/*");
//        registrationBean.setUrlPatterns(urls);
//        return registrationBean;
//    }
//}
//
