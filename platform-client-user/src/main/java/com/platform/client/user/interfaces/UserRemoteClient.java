package com.platform.client.user.interfaces;

import com.platform.client.user.conf.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "platform-provider-user", path = "/demo", configuration = FeignConfiguration.class)
public interface UserRemoteClient {

    @GetMapping("/hello")
    String hello();

}
