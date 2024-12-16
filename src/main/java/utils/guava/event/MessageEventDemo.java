package utils.guava.event;

import com.google.common.eventbus.EventBus;

/**
 * 注册监听到EventBus,并测试消息发送
 */
public class MessageEventDemo {
    public static void main(String[] args) {

        EventBus eventBus = new EventBus("test");

        //注册监听到eventBus
        eventBus.register(new EventListener());

        //通过eventBus发送两笔消息
        eventBus.post(new Event("send message1"));
        eventBus.post(new Event("send message2"));

        //发送不同类型的事件
        eventBus.post(new EventA("EventA Message"));
    }
}
