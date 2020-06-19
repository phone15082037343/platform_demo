package com.platform.client.controller;

import com.platform.client.api.AuthorityClient;
import com.platform.common.module.AuthorityDto;
import com.platform.common.utils.ajax.PageModule;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authority")
@Api(value = "权限实例控制器", tags = "权限实例操作接口")
public class AuthorityController {

    @Autowired
    private AuthorityClient authorityClient;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    @ApiOperation(value = "保存权限实例", notes = "权限实例，参数非空")
    public AuthorityDto save(@ApiParam(value = "权限实例", required = true) @RequestBody AuthorityDto authorityDto) {
        return authorityClient.save(authorityDto);
    }

    @ApiOperation(value = "根据ID查询权限实例", notes = "根据ID查询权限实例，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", required = true)
    })
    @GetMapping("/{authorityId}")
    public AuthorityDto findById(@PathVariable String authorityId) {
        return authorityClient.findById(authorityId);
    }

    @ApiOperation(value = "根据ID删除权限实例", notes = "根据ID权限实例，ID直接拼接在url地址后面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", required = true)
    })
    @DeleteMapping("/{authorityId}")
    public String deleteById(@PathVariable String authorityId) {
        authorityClient.deleteById(authorityId);
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
        return authorityClient.queryPage(pageNo, size);
    }

}
