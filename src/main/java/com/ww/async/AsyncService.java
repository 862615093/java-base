package com.ww.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

    @Async
    public void doAsync01() throws InterruptedException {
        Thread.sleep(3000);
        log.info("=====子线程1执行: " + Thread.currentThread().getName());
    }
 
    @Async
    public void doAsync02() {
        log.info("=====子线程2执行: " + Thread.currentThread().getName());
    }
}