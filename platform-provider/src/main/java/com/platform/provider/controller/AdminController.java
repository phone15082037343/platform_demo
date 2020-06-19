package com.platform.provider.controller;

import com.platform.admin.entity.Administrator;
import com.platform.admin.repository.AdminRepository;
import com.platform.common.module.AdminDto;
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

    @ApiOperation(value = "根据ID查询系统管理员", notes = "根据ID查询系统管理员，ID直接拼接在url地址后面")
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

    @ApiOperation(value = "根据ID删除系统管理员", notes = "根据ID删除系统管理员，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", required = true)
    })
    @DeleteMapping("/{adminId}")
    public String deleteById(@PathVariable String adminId) {
        adminRepository.deleteById(adminId);
        return adminId;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询，这里只是简单做了一个demo")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", defaultValue = "1", value = "当前页号"),
            @ApiImplicitParam(name = "size", defaultValue = "10", value = "每页展示条数")
    })
    public PageModule<AdminDto> queryPage(@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        Page<Administrator> page = adminRepository.findAll(PageRequest.of(pageNo - 1, size));
        PageModule<AdminDto> pm = new PageModule<>();
        pm.setCurrentPage(pageNo);
        pm.setSize(size);
        pm.setTotalCount(page.getTotalElements());
        pm.setTotalPage(page.getTotalPages());
        List<AdminDto> list = page.get().map(administrator -> {
            AdminDto adminDto = new AdminDto();
            BeanUtils.copyProperties(administrator, adminDto);
            return adminDto;
        }).collect(Collectors.toList());
        pm.setList(list);
        return pm;
    }

}
