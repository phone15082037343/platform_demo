package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.RoleFallbackFactory;
import com.platform.common.module.RoleDto;
import com.platform.common.utils.ajax.PageModule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "platform-provider", path = "/role", configuration = FeignConfiguration.class, fallbackFactory = RoleFallbackFactory.class)
public interface RoleClient {

    @PostMapping
    RoleDto save(@RequestBody RoleDto roleDto);

    @GetMapping("/{roleId}")
    RoleDto findById(@PathVariable(name = "roleId") String roleId);

    @DeleteMapping("/{roleId}")
    void deleteById(@PathVariable(name = "roleId") String roleId);

    @GetMapping
    PageModule<RoleDto> queryPage(@RequestParam(name = "pageNo", required = false) int pageNo, @RequestParam(name = "size", required = false) int size);

}
