package com.platform.common.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "com.platform.common.module.AdminRoleDto", description = "管理员角色授权参数")
public class AdminRoleDto implements Serializable {

    @ApiModelProperty(value = "管理员ID")
    private String adminId;

    @ApiModelProperty(value = "角色ID集合，为空代表清空该用户角色")
    private List<String> roleIds = new ArrayList<>();


}
