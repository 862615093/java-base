package com.ww.spring.bean.lifeCycleBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class LifeCycleBean {

    public LifeCycleBean() {
        log.debug("1、构造（实例化）");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home) {
        log.debug("2、依赖注入: {}", home);
    }

    @PostConstruct
    public void init() {
        log.debug("3、初始化");
    }

    @PreDestroy
    public void destroy() {
        log.debug("4、销毁");
    }
}