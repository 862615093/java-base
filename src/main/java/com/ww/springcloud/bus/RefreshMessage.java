package com.ww.springcloud.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 业务类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshMessage extends RemoteApplicationEvent {

    private String msg;

    public RefreshMessage() {
    }


    public RefreshMessage(Object source, String originService, String destinationService, String msg) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.msg = msg;
    }

}