package com.platform.proxy.conf;

import com.platform.admin.entity.AdminRole;
import com.platform.admin.entity.Administrator;
import com.platform.admin.entity.Role;
import com.platform.admin.repository.AdminRepository;
import com.platform.admin.repository.AdminRoleRepository;
import com.platform.admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 查询用户拥有的角色
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator = adminRepository.findByUsername(username);
        if (administrator == null) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        }

        // 获取用户角色
        List<AdminRole> adminRoleList = adminRoleRepository.findByAdminId(administrator.getAdminId());
        // 角色ID
        List<String> roleIds = adminRoleList.stream().map(userRole -> userRole.getRole().getRoleId()).collect(Collectors.toList());
        // 角色列表
        List<Role> roleList = roleRepository.findByRoleIds(roleIds);
        List<SimpleGrantedAuthority> list = roleList.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(administrator.getUsername(), administrator.getPassword(), list);
    }

}
