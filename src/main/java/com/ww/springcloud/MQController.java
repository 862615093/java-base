package com.ww.springcloud;

//import com.ww.springcloud.bus.BusProducer;
//import com.ww.springcloud.stream.StreamProducer;
import com.ww.springcloud.bus.BusProducer;
import com.ww.springcloud.bus.RefreshMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Tag(name = "MQ测试")
@RequestMapping("/mq")
public class MQController {

//    @Autowired
//    private StreamProducer streamProducer;


        @Autowired
        private BusProducer busProducer;


//    @GetMapping("t1")
//    @Operation(summary = "stream测试")
//    public String t1() {
//        streamProducer.sendMessage("测试 stream...");
//        return "ok~";
//    }

    @GetMapping("t2")
    @Operation(summary = "bus测试")
    public String t2() {
        busProducer.sendMessage("测试 bus...");
        return "ok~";
    }

}