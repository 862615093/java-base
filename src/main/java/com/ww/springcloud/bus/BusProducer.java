package com.ww.springcloud.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BusProducer extends AbstractBusProducer {

    public void sendMessage(String msg) {
        publishEvent(new RefreshMessage(this, getBusId(), selfDestinationService(), msg));
    }


}
