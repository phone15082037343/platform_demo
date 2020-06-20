package com.platform.client.controller;

import com.platform.client.api.RoleAuthorityClient;
import com.platform.common.module.RoleAuthorityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roleauthority")
@Api(value = "角色权限", tags = "角色权限操作")
public class RoleAuthorityController {

    @Autowired
    private RoleAuthorityClient roleAuthorityClient;

    @PostMapping("/grant")
    @ApiOperation(value = "角色授权", notes = "将权限授角色")
    public String grant(@ApiParam(value = "将权限授角色", required = true) @RequestBody RoleAuthorityDto roleAuthorityDto) {
        return roleAuthorityClient.grant(roleAuthorityDto);
    }

}
