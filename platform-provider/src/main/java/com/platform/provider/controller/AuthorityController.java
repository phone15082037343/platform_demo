package com.platform.provider.controller;

import com.platform.admin.entity.Authority;
import com.platform.admin.repository.AuthorityRepository;
import com.platform.common.module.AuthorityDto;
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
@RequestMapping("/authority")
@Api(value = "权限实例控制器", tags = "权限实例操作接口")
public class AuthorityController {

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    @ApiOperation(value = "保存权限实例", notes = "权限实例，参数非空")
    public AuthorityDto save(@ApiParam(value = "权限实例", required = true) @RequestBody AuthorityDto authorityDto) {
        Authority authority = new Authority();
        BeanUtils.copyProperties(authorityDto, authority);
        authorityRepository.save(authority);
        BeanUtils.copyProperties(authority, authorityDto);
        return authorityDto;
    }

    @ApiOperation(value = "根据ID查询权限实例", notes = "根据ID查询权限实例，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", required = true)
    })
    @GetMapping("/{authorityId}")
    public AuthorityDto findById(@PathVariable String authorityId) {
        Authority authority = authorityRepository.findById(authorityId).orElse(null);
        AuthorityDto authorityDto = new AuthorityDto();
        BeanUtils.copyProperties(Objects.requireNonNull(authority), authorityDto);
        return authorityDto;
    }

    @ApiOperation(value = "根据ID删除权限实例", notes = "根据ID权限实例，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", required = true)
    })
    @DeleteMapping("/{authorityId}")
    public String deleteById(@PathVariable String authorityId) {
        authorityRepository.deleteById(authorityId);
        return authorityId;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询，这里只是简单做了一个demo")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", defaultValue = "1", value = "当前页号"),
            @ApiImplicitParam(name = "size", defaultValue = "10", value = "每页展示条数")
    })
    public PageModule<AuthorityDto> queryPage(@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        Page<Authority> page = authorityRepository.findAll(PageRequest.of(pageNo - 1, size));
        PageModule<AuthorityDto> pm = new PageModule<>();
        pm.setCurrentPage(pageNo);
        pm.setSize(size);
        pm.setTotalCount(page.getTotalElements());
        pm.setTotalPage(page.getTotalPages());
        List<AuthorityDto> list = page.get().map(authority -> {
            AuthorityDto authorityDto = new AuthorityDto();
            BeanUtils.copyProperties(authority, authorityDto);
            return authorityDto;
        }).collect(Collectors.toList());
        pm.setList(list);
        return pm;
    }

}
