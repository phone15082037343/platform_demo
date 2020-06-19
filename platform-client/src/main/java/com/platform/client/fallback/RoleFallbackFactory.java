package com.platform.client.fallback;

import com.platform.client.api.RoleClient;
import com.platform.common.module.RoleDto;
import com.platform.common.utils.ajax.PageModule;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleFallbackFactory implements FallbackFactory<RoleClient> {

    @Override
    public RoleClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new RoleClient() {
            @Override
            public RoleDto save(RoleDto roleDto) {
                return new RoleDto();
            }

            @Override
            public RoleDto findById(String roleId) {
                return new RoleDto();
            }

            @Override
            public void deleteById(String roleId) {

            }

            @Override
            public PageModule<RoleDto> queryPage(int pageNo, int size) {
                return new PageModule<>();
            }

        };
    }

}
