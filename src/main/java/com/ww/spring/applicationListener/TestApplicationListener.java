package com.ww.frame.spring.applicationListener;

import com.ww.frame.spring.applicationListener.customEvent.EmailEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 内置事件
 * 业务方监听事件举例
 * 比如要监听ContextRefreshedEvent的时可以实现ApplicationListener接口，并且传入要监听的事件
 */
//@Component
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println(contextRefreshedEvent);
        System.out.println("TestApplicationListener............................");
    }
}