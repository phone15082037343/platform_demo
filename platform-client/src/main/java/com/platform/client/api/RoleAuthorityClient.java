package com.platform.client.api;

import com.platform.client.conf.FeignConfiguration;
import com.platform.client.fallback.AdminRoleFallbackFactory;
import com.platform.common.module.AdminRoleDto;
import com.platform.common.module.RoleAuthorityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "platform-provider", path = "/roleauthority", configuration = FeignConfiguration.class, fallbackFactory = AdminRoleFallbackFactory.class)
public interface RoleAuthorityClient {

    @PostMapping("/grant")
    String grant(@RequestBody RoleAuthorityDto roleAuthorityDto);


}
