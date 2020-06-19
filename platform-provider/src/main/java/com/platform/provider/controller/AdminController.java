package com.platform.provider.controller;

import com.platform.common.module.AdminDto;
import com.platform.provider.entity.Administrator;
import com.platform.provider.repository.AdminRepository;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/admin")
@Api(value = "系统管理员控制器", tags = "系统管理员操作接口")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    @ApiOperation(value = "保存系统管理员", notes = "保存系统管理员，参数非空")
    public AdminDto save(@ApiParam(value = "保存系统管理员参数", required = true) @RequestBody AdminDto adminDto) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(adminDto, administrator);
        adminRepository.save(administrator);
        BeanUtils.copyProperties(administrator, adminDto);
        return adminDto;
    }

    @ApiOperation(value = "根据ID查询系统管理员", notes = "根据ID查询系统管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", required = true)
    })
    @GetMapping("/{adminId}")
    public AdminDto findById(@PathVariable String adminId) {
        Administrator administrator = adminRepository.findById(adminId).orElse(null);
        AdminDto adminDto = new AdminDto();
        BeanUtils.copyProperties(Objects.requireNonNull(administrator), adminDto);
        return adminDto;
    }

    @ApiOperation(value = "根据ID删除系统管理员", notes = "根据ID删除系统管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", required = true)
    })
    @DeleteMapping("/{adminId}")
    public String deleteById0(@PathVariable String adminId) {
        adminRepository.deleteById(adminId);
        return adminId;
    }

}
