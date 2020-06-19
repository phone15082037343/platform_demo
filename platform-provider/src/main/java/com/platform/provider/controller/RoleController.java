package com.platform.provider.controller;

import com.platform.admin.entity.Role;
import com.platform.admin.repository.RoleRepository;
import com.platform.common.module.RoleDto;
import com.platform.common.utils.ajax.PageModule;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(value = "角色管理", tags = "针对系统角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    @ApiOperation(value = "保存系统角色", notes = "保存系统角色")
    public RoleDto save(@ApiParam(value = "保存系统角色", required = true) @RequestBody RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        roleRepository.save(role);
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }

    @ApiOperation(value = "根据ID查询系统角色", notes = "根据ID查询系统角色，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true)
    })
    @GetMapping("/{roleId}")
    public RoleDto findById(@PathVariable String roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(Objects.requireNonNull(role), roleDto);
        return roleDto;
    }

    @ApiOperation(value = "根据ID删除系统角色", notes = "根据ID删除系统角色，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true)
    })
    @DeleteMapping("/{roleId}")
    public String deleteById(@PathVariable String roleId) {
        roleRepository.deleteById(roleId);
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
        Page<Role> page = roleRepository.findAll(PageRequest.of(pageNo - 1, size));
        PageModule<RoleDto> pm = new PageModule<>();
        pm.setCurrentPage(pageNo);
        pm.setSize(size);
        pm.setTotalCount(page.getTotalElements());
        pm.setTotalPage(page.getTotalPages());
        List<RoleDto> list = page.get().map(role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        pm.setList(list);
        return pm;
    }

}
