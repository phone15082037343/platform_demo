package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.AdminFallbackFactory;
import com.platform.common.module.AdminDto;
import com.platform.common.utils.ajax.PageModule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "platform-provider", path = "/admin", configuration = FeignConfiguration.class, fallbackFactory = AdminFallbackFactory.class)
public interface AdminClient {

    @PostMapping
    AdminDto save(@RequestBody AdminDto adminDto);

    @GetMapping("/{adminId}")
    AdminDto findById(@PathVariable(name = "adminId") String adminId);

    @DeleteMapping("/{adminId}")
    void deleteById(@PathVariable(name = "adminId") String adminId);

    @GetMapping
    PageModule<AdminDto> queryPage(@RequestParam(name = "pageNo", required = false) int pageNo, @RequestParam(name = "size", required = false) int size);

}
