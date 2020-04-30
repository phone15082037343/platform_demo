package com.platform.client.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call/hello")
    public String callHello() {
        return restTemplate.getForObject("http://platform-provider-user/demo/hello", String.class);
    }

}
