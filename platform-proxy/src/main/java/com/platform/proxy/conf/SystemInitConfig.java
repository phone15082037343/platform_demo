package com.platform.proxy.conf;

import com.platform.admin.entity.Administrator;
import com.platform.admin.entity.Authority;
import com.platform.admin.entity.Role;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.system")
public class SystemInitConfig {

    /** 是否初始化 */
    private boolean init;
    /** 超级管理员 */
    private Administrator admin;
    /** 角色列表 */
    private List<Role> roles;
    /** 权限实例 */
    private List<Authority> authorities;

}
