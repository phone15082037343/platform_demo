package com.platform.proxy.listener;

import com.platform.admin.entity.*;
import com.platform.admin.repository.*;
import com.platform.proxy.conf.SystemInitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 程序初始化完成后执行
 */
@Component
public class StartAddDataListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SystemInitConfig initConfig;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!initConfig.isInit()) {
            return;
        }

        // 清空用户角色、角色权限、权限实例、角色实例，更新超级管理员
        adminRoleRepository.deleteAll();
        roleAuthorityRepository.deleteAll();
        authorityRepository.deleteAll();
        roleRepository.deleteAll();
        Administrator admin = initConfig.getAdmin();
        Administrator dbAdmin = adminRepository.findByUsername(admin.getUsername());
        if (dbAdmin != null) {
            adminRepository.delete(dbAdmin);
        }

        // 添加权限、角色、管理员、角色权限、管理员角色
        List<Authority> authorities = initConfig.getAuthorities();
        authorityRepository.saveAll(authorities);
        // 角色
        List<Role> roles = initConfig.getRoles();
        roleRepository.saveAll(roles);
        // 角色权限
        roles.forEach(role -> {
            authorities.forEach(authority -> {
                RoleAuthority roleAuthority = new RoleAuthority();
                roleAuthority.setRole(role);
                roleAuthority.setAuthority(authority);
                roleAuthorityRepository.save(roleAuthority);
            });
        });

        // 添加管理员
        // 加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);

        // 角色权限
        roles.forEach(role -> {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdmin(admin);
            adminRole.setRole(role);
            adminRoleRepository.save(adminRole);
        });

    }

}
