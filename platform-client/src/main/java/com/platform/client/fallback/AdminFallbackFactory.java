package com.platform.client.fallback;

import com.platform.client.api.AdminClient;
import com.platform.common.module.AdminDto;
import com.platform.common.utils.ajax.PageModule;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminFallbackFactory implements FallbackFactory<AdminClient> {

    @Override
    public AdminClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new AdminClient() {
            @Override
            public AdminDto save(AdminDto adminDto) {
                return new AdminDto();
            }

            @Override
            public AdminDto findById(String adminId) {
                return new AdminDto();
            }

            @Override
            public void deleteById(String adminId) {

            }

            @Override
            public PageModule<AdminDto> queryPage(int pageNo, int size) {
                return new PageModule<>();
            }
        };
    }

}
