package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.AdminRoleFallbackFactory;
import com.platform.common.module.AdminRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "platform-provider", path = "/adminrole", configuration = FeignConfiguration.class, fallbackFactory = AdminRoleFallbackFactory.class)
public interface AdminRoleClient {

    @PostMapping("/grant")
    String grant(@RequestBody AdminRoleDto adminRoleDto);


}
