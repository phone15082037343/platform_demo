package com.platform.provider.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.platform.provider.entity.UpdateAdmin", description = "更新系统管理员参数")
public class UpdateAdmin implements Serializable {

    @ApiModelProperty(value = "管理员ID")
    private String adminId;

    @ApiModelProperty(value = "密码，明文", required = true)
    private String password;

    @ApiModelProperty(value = "是否禁用，默认为false")
    private boolean disable;

}
