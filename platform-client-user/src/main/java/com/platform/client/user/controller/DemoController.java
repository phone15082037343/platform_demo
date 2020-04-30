package com.platform.client.user.controller;

import com.platform.client.user.interfaces.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/call/hello")
    public String callHello() {
        return userRemoteClient.hello();
    }

}
