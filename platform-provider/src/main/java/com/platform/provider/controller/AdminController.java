package com.platform.provider.controller;

import com.platform.provider.entity.AddAdmin;
import com.platform.provider.entity.Administrator;
import com.platform.provider.repository.AdminRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/admin")
@Api(value = "系统管理员控制器", tags = "系统管理员操作接口")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @ApiOperation(value = "添加系统管理员", notes = "添加系统管理员，参数非空")
    @PostMapping
    public Administrator save(@ApiParam(value = "添加系统管理员参数", required = true) @RequestBody AddAdmin addAdmin) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(addAdmin, administrator);
        administrator.setCreateTime(new Date());
        adminRepository.save(administrator);
        return administrator;
    }

}
