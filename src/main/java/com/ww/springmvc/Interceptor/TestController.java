package com.ww.springmvc.Interceptor;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = {"过滤器，拦截器，切面测试"})
@RequestMapping("/filter")
public class TestController {

    @GetMapping("t1")
    public String filter() {
        System.out.println("filter...");
        return "ok~";
    }

}