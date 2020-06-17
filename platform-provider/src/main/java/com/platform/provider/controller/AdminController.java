package com.platform.provider.controller;

import com.platform.provider.entity.AddAdmin;
import com.platform.provider.entity.Administrator;
import com.platform.provider.entity.Response;
import com.platform.provider.entity.UpdateAdmin;
import com.platform.provider.repository.AdminRepository;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin")
@Api(value = "系统管理员控制器", tags = "系统管理员操作接口")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @ApiOperation(value = "添加系统管理员", notes = "添加系统管理员，参数非空")
    @PostMapping
    public Response save(@ApiParam(value = "添加系统管理员参数", required = true) @RequestBody AddAdmin addAdmin) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(addAdmin, administrator);
        administrator.setCreateTime(new Date());
        adminRepository.save(administrator);
        return new Response(Response.SUCCESS, "添加管理员成功", administrator);
    }

    @PutMapping
    @ApiOperation(value = "更新系统管理员", notes = "更新系统管理员参数")
    public Administrator update(@ApiParam(value = "更新系统管理员参数", required = true) @RequestBody UpdateAdmin updateAdmin) {
        Administrator administrator = adminRepository.findByAdminId(updateAdmin.getAdminId());
        BeanUtils.copyProperties(updateAdmin, administrator);
        administrator.setLastUpdate(new Date());
        adminRepository.save(administrator);
        return administrator;
    }

    @PutMapping("/{adminId}")
    @ApiOperation(value = "更新系统管理员", notes = "更新系统管理员参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", required = true)
    })
    public Administrator update(@PathVariable String adminId, @ApiParam(value = "更新系统管理员参数", required = true) @RequestBody UpdateAdmin updateAdmin) {
        Administrator administrator = adminRepository.findByAdminId(adminId);
        BeanUtils.copyProperties(updateAdmin, administrator);

        administrator.setAdminId(adminId);
        administrator.setLastUpdate(new Date());
        adminRepository.save(administrator);
        return administrator;
    }

}
