package com.platform.provider.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.platform.provider.entity.AddAdmin", description = "添加系统管理员参数")
public class AddAdmin implements Serializable {

    @ApiModelProperty(value = "登录账号", required = true)
    private String username;

    @ApiModelProperty(value = "密码，明文", required = true)
    private String password;

    @ApiModelProperty(value = "是否禁用，默认为false")
    private boolean disable;

}
