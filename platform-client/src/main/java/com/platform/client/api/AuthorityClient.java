package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.AuthorityFallbackFactory;
import com.platform.common.module.AuthorityDto;
import com.platform.common.utils.ajax.PageModule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "platform-provider", path = "/authority", configuration = FeignConfiguration.class, fallbackFactory = AuthorityFallbackFactory.class)
public interface AuthorityClient {

    @PostMapping
    AuthorityDto save(@RequestBody AuthorityDto authorityDto);

    @GetMapping("/{authorityId}")
    AuthorityDto findById(@PathVariable(name = "authorityId") String authorityId);

    @DeleteMapping("/{authorityId}")
    void deleteById(@PathVariable(name = "authorityId") String authorityId);

    @GetMapping
    PageModule<AuthorityDto> queryPage(@RequestParam(name = "pageNo", required = false) int pageNo, @RequestParam(name = "size", required = false) int size);

}
