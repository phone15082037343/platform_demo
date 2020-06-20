package com.platform.common.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "com.platform.common.module.RoleAuthorityDto", description = "角色权限授权参数")
public class RoleAuthorityDto implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "权限ID集合")
    private List<String> authorityList = new ArrayList<>();

}
