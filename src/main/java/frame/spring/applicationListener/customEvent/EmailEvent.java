package frame.spring.applicationListener.customEvent;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * 可以自定义事件，然后做完业务处理后手动发出。集成某个监听接口，接收到事件后进行业务处理
 */
public class EmailEvent extends ApplicationEvent {
    private String address;
    private String text;

    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public EmailEvent(Object source) {
        super(source);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}