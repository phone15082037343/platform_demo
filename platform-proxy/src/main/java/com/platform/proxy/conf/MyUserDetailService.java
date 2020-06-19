package com.platform.proxy.conf;

import com.platform.admin.entity.Administrator;
import com.platform.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 查询用户拥有的角色
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator = adminRepository.findByUsername(username);
        if (administrator == null) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        }

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ADMIN"));
        list.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(administrator.getUsername(), administrator.getPassword(), list);
    }

}
