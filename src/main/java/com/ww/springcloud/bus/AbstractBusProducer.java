package com.ww.springcloud.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * 基于 Spring Cloud Bus 实现的 Producer 抽象类
 *
 * @author 芋道源码
 */
@Slf4j
public abstract class AbstractBusProducer {

    @Resource
    protected ApplicationEventPublisher applicationEventPublisher;

    @Resource
    protected ServiceMatcher serviceMatcher;

    @Value("${spring.application.name}")
    protected String applicationName;

    protected void publishEvent(RemoteApplicationEvent event) {
        log.info("bus 事件发布...");
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * @return 只广播给自己服务的实例
     */
    protected String selfDestinationService() {
        return applicationName + ":**";
    }

    protected String getBusId() {
        return serviceMatcher.getBusId();
    }

}
