package com.platform.client.fallback;

import com.platform.client.api.AdminRoleClient;
import com.platform.client.api.RoleClient;
import com.platform.common.module.AdminRoleDto;
import com.platform.common.module.RoleDto;
import com.platform.common.utils.ajax.PageModule;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminRoleFallbackFactory implements FallbackFactory<AdminRoleClient> {

    @Override
    public AdminRoleClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new AdminRoleClient() {
            @Override
            public String grant(AdminRoleDto adminRoleDto) {
                return "";
            }
        };
    }

}
