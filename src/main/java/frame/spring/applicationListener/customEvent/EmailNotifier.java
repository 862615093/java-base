package frame.spring.applicationListener.customEvent;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 监听定义
 */
//@Component
public class EmailNotifier implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof EmailEvent) {
            EmailEvent emailEvent = (EmailEvent) event;
            System.out.println("邮件地址：" + emailEvent.getAddress());
            System.out.println("邮件内容：" + emailEvent.getText());
        } else {
            System.out.println("容器本身事件：" + event);
        }
    }
}