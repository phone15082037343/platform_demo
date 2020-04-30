package com.platform.provider.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        log.info("logger hello world");
        return "hello world";
    }

}
