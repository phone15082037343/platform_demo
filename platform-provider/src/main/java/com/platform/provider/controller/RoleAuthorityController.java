package com.platform.provider.controller;

import com.platform.admin.entity.Authority;
import com.platform.admin.entity.Role;
import com.platform.admin.entity.RoleAuthority;
import com.platform.admin.repository.RoleAuthorityRepository;
import com.platform.common.module.RoleAuthorityDto;
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
@RequestMapping("/roleauthority")
@Api(value = "角色权限", tags = "角色权限操作")
public class RoleAuthorityController {

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @PostMapping("/grant")
    @ApiOperation(value = "角色授权", notes = "将权限授角色")
    public String grant(@ApiParam(value = "将权限授角色", required = true) @RequestBody RoleAuthorityDto roleAuthorityDto) {
        // 客户端传值相对数据库查询结果
        // 根绝角色ID查询该角色具有的权限
        List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findbyRoleId(roleAuthorityDto.getRoleId());
        // 数据库查询的权限ID集合
        List<String> dbAuthorityList = roleAuthorityList.stream().map(roleAuthority -> roleAuthority.getAuthority().getAuthorityId()).collect(Collectors.toList());

        // 参数ID集合
        List<String> paramsAuthorityIds = roleAuthorityDto.getAuthorityList();
        // 临时保存
        List<String> temp = new ArrayList<>(paramsAuthorityIds);

        // 多的，数据库添加
        temp.removeAll(dbAuthorityList);
        if (paramsAuthorityIds.size() > 0) {
            roleAuthorityRepository.saveAll(convert(roleAuthorityDto.getRoleId(), temp));
        }

        // 删的，数据库删除
        dbAuthorityList.removeAll(paramsAuthorityIds);
        if (dbAuthorityList.size() > 0) {
            dbAuthorityList.forEach(authorityId -> roleAuthorityRepository.delete(roleAuthorityDto.getRoleId(), authorityId));
        }

        return "success";
    }

    /**
     * 转换
     * @param roleId 角色ID
     * @param authorityIdList 权限ID集合
     */
    private List<RoleAuthority> convert(String roleId, List<String> authorityIdList) {
        return authorityIdList.stream().map(authorityId -> {
            Role role = new Role();
            role.setRoleId(roleId);
            Authority authority = new Authority();
            authority.setAuthorityId(authorityId);

            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRole(role);
            roleAuthority.setAuthority(authority);
            return roleAuthority;
        }).collect(Collectors.toList());
    }

}
