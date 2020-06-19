package com.platform.common.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限实体，针对URL
 */
@Data
@ApiModel(value = "com.platform.common.module.AuthorityDto", description = "保存权限实例")
public class AuthorityDto implements Serializable {

    @ApiModelProperty(value = "权限ID")
    private String authorityId;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限路径")
    private String authorityUrl;

}