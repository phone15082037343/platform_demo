package com.platform.client.user.controller;

import com.platform.client.user.interfaces.UserRemoteClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserRemoteClient userRemoteClient;

    @ApiOperation(value = "hello test", notes = "notes")
    @GetMapping("/call/hello")
    public String callHello(@ApiParam(value = "name", required = false) String name) {
        System.out.println(name);
        return userRemoteClient.hello();
    }

}
