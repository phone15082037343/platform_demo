package com.platform.client.controller;

import com.platform.client.api.RoleClient;
import com.platform.common.module.RoleDto;
import com.platform.common.utils.ajax.PageModule;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "角色管理", tags = "针对系统角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleClient roleClient;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    @ApiOperation(value = "保存系统角色", notes = "保存系统角色")
    public RoleDto save(@ApiParam(value = "保存系统角色", required = true) @RequestBody RoleDto roleDto) {
        return roleClient.save(roleDto);
    }

    @ApiOperation(value = "根据ID查询系统角色", notes = "根据ID查询系统角色，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true)
    })
    @GetMapping("/{roleId}")
    public RoleDto findById(@PathVariable String roleId) {
        return roleClient.findById(roleId);
    }

    @ApiOperation(value = "根据ID删除系统角色", notes = "根据ID删除系统角色，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true)
    })
    @DeleteMapping("/{roleId}")
    public String deleteById(@PathVariable String roleId) {
        roleClient.deleteById(roleId);
        return roleId;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询，这里只是简单做了一个demo")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", defaultValue = "1", value = "当前页号"),
            @ApiImplicitParam(name = "size", defaultValue = "10", value = "每页展示条数")
    })
    public PageModule<RoleDto> queryPage(@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return roleClient.queryPage(pageNo, size);
    }

}
