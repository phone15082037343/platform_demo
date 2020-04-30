package com.platform.client.user.interfaces;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientFactory implements FallbackFactory<UserRemoteClient> {

    @Override
    public UserRemoteClient create(Throwable throwable) {
        return new UserRemoteClient() {
            @Override
            public String hello() {
                return "error: hello";
            }
        };
    }

}
