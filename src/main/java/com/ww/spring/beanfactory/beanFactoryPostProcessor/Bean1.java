package com.ww.spring.beanfactory.beanFactoryPostProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bean1 {


    public Bean1() {
        log.debug("我被 Spring 管理啦");
    }
}
