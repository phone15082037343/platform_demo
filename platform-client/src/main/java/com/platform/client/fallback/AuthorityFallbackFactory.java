package com.platform.client.fallback;

import com.platform.client.api.AuthorityClient;
import com.platform.common.module.AuthorityDto;
import com.platform.common.utils.ajax.PageModule;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorityFallbackFactory implements FallbackFactory<AuthorityClient> {

    @Override
    public AuthorityClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new AuthorityClient() {

            @Override
            public AuthorityDto save(AuthorityDto authorityDto) {
                return new AuthorityDto();
            }

            @Override
            public AuthorityDto findById(String authorityId) {
                return new AuthorityDto();
            }

            @Override
            public void deleteById(String authorityId) {

            }

            @Override
            public PageModule<AuthorityDto> queryPage(int pageNo, int size) {
                return new PageModule<>();
            }
        };
    }

}
