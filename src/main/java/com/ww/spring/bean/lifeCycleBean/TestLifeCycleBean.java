package com.ww.spring.bean.lifeCycleBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestLifeCycleBean {

    /*
    bean 的生命周期, 以及 bean 后处理器

    学到了什么
        a. Spring bean 生命周期各个阶段
        b. 模板设计模式, 大流程已经固定好了, 通过接口回调(bean 后处理器)扩展
    */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestLifeCycleBean.class, args);
        context.close();
    }



}
