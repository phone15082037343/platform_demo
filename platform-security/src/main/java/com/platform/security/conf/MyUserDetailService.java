package com.platform.security.conf;

import com.platform.security.entity.Role;
import com.platform.security.entity.User;
import com.platform.security.entity.UserRole;
import com.platform.security.repository.RoleRepository;
import com.platform.security.repository.UserRepository;
import com.platform.security.repository.UserRoleRepository;
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
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 获取用户角色
        List<UserRole> userRoleList = userRoleRepository.findByUserId(user.getUserId());
        // 角色ID
        List<String> roleIds = userRoleList.stream().map(userRole -> userRole.getRole().getRoleId()).collect(Collectors.toList());
        // 角色列表
        List<Role> roleList = roleRepository.findByRoleIds(roleIds);
        List<SimpleGrantedAuthority> list = roleList.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }

}
