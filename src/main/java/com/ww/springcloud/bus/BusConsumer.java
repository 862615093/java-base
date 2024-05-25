package com.ww.springcloud.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 针对 {@link RefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class BusConsumer {

    @EventListener
    public void execute(RefreshMessage message) {
        log.info("bus 事件监听...");
        log.info("[execute][收到 SmsTemplate 刷新消息 msg = {}]", message.getMsg());

    }

}
