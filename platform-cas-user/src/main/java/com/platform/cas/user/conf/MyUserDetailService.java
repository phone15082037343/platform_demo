package com.platform.cas.user.conf;

import com.platform.cas.user.entity.AdminRole;
import com.platform.cas.user.entity.Administrator;
import com.platform.cas.user.repository.AdminRoleRepository;
import com.platform.cas.user.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 数据库查询
        Administrator administrator = administratorRepository.findByUsername(username);
        if (administrator == null) {
            throw new UsernameNotFoundException("该用户不存在:" + username);
        }

        // 查询该用户拥有的角色集合
        List<AdminRole> adminRoleList = adminRoleRepository.findByAdminId(administrator.getAdminId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>(adminRoleList.size());
        adminRoleList.forEach(adminRole -> grantedAuthorityList.add(new SimpleGrantedAuthority(adminRole.getRole().getName())));
        administrator.setAuthorities(grantedAuthorityList);
        return administrator;
    }

}
