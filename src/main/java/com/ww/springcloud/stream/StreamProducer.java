//package com.ww.springcloud.stream;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.function.StreamBridge;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//
//@Slf4j
//@Component
//public class StreamProducer {
//
//    @Resource
//    private StreamBridge streamBridge;
//
//
//    public void sendMessage(String message) {
//        streamBridge.send("streamSend-out-0", message);
//    }
//
//
//}
