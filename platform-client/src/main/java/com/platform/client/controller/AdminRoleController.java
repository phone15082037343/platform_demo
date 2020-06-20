package com.platform.client.controller;

import com.platform.client.api.AdminRoleClient;
import com.platform.common.module.AdminRoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminrole")
@Api(value = "管理员角色授权", tags = "将角色授权给管理员")
public class AdminRoleController {

    @Autowired
    private AdminRoleClient adminRoleClient;

    @PostMapping("/grant")
    @ApiOperation(value = "用户授权", notes = "将角色授予用户")
    public String grant(@ApiParam(value = "将角色授权给管理员", required = true) @RequestBody AdminRoleDto adminRoleDto) {
        return adminRoleClient.grant(adminRoleDto);
    }

}
