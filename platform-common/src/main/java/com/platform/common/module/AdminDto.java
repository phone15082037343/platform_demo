package com.platform.common.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.platform.common.module.AdminDto", description = "保存系统管理员参数")
public class AdminDto implements Serializable {

    @ApiModelProperty(value = "管理员ID")
    private String adminId;

    @ApiModelProperty(value = "登录账号，唯一")
    private String username;

    @ApiModelProperty(value = "密码，明文")
    private String password;

    @ApiModelProperty(value = "是否禁用，默认为false")
    private boolean disable;

}
