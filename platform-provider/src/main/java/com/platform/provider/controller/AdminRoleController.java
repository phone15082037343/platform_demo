package com.platform.provider.controller;

import com.platform.admin.entity.AdminRole;
import com.platform.admin.entity.Administrator;
import com.platform.admin.entity.Role;
import com.platform.admin.repository.AdminRoleRepository;
import com.platform.common.module.AdminRoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminrole")
@Api(value = "管理员角色授权", tags = "将角色授权给管理员")
public class AdminRoleController {

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @PostMapping("/grant")
    @ApiOperation(value = "用户授权", notes = "将角色授予用户")
    public String grant(@ApiParam(value = "将角色授权给管理员", required = true) @RequestBody AdminRoleDto adminRoleDto) {
        // 客户端传值相对数据库查询结果
        List<AdminRole> adminRoleList = adminRoleRepository.findByAdminId(adminRoleDto.getAdminId());
        // 数据库查询的角色ID集合
        List<String> dbRoleList = adminRoleList.stream().map(adminRole -> adminRole.getRole().getRoleId()).collect(Collectors.toList());

        // 参数ID集合
        List<String> paramsRoleIds = adminRoleDto.getRoleIds();
        // 临时保存
        List<String> temp = new ArrayList<>(paramsRoleIds);

        // 多的，数据库添加
        temp.removeAll(dbRoleList);
        if (paramsRoleIds.size() > 0) {
            adminRoleRepository.saveAll(convert(adminRoleDto.getAdminId(), temp));
        }

        // 删的，数据库删除
        dbRoleList.removeAll(paramsRoleIds);
        if (dbRoleList.size() > 0) {
            dbRoleList.forEach(roleId -> adminRoleRepository.delete(adminRoleDto.getAdminId(), roleId));
        }

        return "success";
    }

    /**
     * 转换
     * @param adminId 管理员ID
     * @param roleIds 角色ID集合
     */
    private List<AdminRole> convert(String adminId, List<String> roleIds) {
        return roleIds.stream().map(roleId -> {
            Administrator admin = new Administrator();
            admin.setAdminId(adminId);
            Role role = new Role();
            role.setRoleId(roleId);

            AdminRole adminRole = new AdminRole();
            adminRole.setAdmin(admin);
            adminRole.setRole(role);
            return adminRole;
        }).collect(Collectors.toList());
    }

}
