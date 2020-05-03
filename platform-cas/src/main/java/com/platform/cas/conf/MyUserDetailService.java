package com.platform.cas.conf;

import com.platform.cas.entity.Administrator;
import com.platform.cas.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 数据库查询
        Administrator administrator = administratorRepository.findByUsername(username);
        if (administrator == null) {
            throw new UsernameNotFoundException("该用户不存在:" + username);
        }

        // TODO 解析权限集合
        administrator.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(administrator.getRoles()));
        return administrator;
    }

}
