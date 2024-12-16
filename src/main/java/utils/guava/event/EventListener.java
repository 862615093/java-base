package utils.guava.event;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * 定义事件监听
 */
public class EventListener {

    @Subscribe
    public void listener(Event event){
        System.out.println("thread-" + Thread.currentThread().getName() + "-Subscribe 接收消息:" + event.getMessage());
    }

//    @Subscribe
//    public void listenerEventA(EventA eventA){
//        System.out.println("subscribe EventA:" + eventA.getMessage());
//    }


    // DeadEvent就是用来接收这种没有订阅者的消息
    @Subscribe
    public void listenerDeadEvent(DeadEvent deadEvent){
        System.out.println("deadEvent:" + deadEvent.getEvent());
    }

}
