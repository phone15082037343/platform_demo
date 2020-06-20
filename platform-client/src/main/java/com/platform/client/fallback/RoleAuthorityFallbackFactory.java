package com.platform.client.fallback;

import com.platform.client.api.RoleAuthorityClient;
import com.platform.common.module.RoleAuthorityDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthorityFallbackFactory implements FallbackFactory<RoleAuthorityClient> {

    @Override
    public RoleAuthorityClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new RoleAuthorityClient() {
            @Override
            public String grant(RoleAuthorityDto roleAuthorityDto) {
                return "";
            }
        };
    }

}
