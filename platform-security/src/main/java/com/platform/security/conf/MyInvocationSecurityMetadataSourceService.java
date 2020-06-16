package com.platform.security.conf;

import com.platform.security.entity.Authority;
import com.platform.security.entity.Role;
import com.platform.security.entity.RoleAuthority;
import com.platform.security.repository.AuthorityRepository;
import com.platform.security.repository.RoleAuthorityRepository;
import com.platform.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
     * 用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getHttpRequest();

        // TODO 需要考虑  /user/2，/user/3，这种情况

        // 查询所有权限
        List<Authority> authorityList = authorityRepository.findAll();
        // 权限示例集合
        List<String> authorities = authorityList.stream().filter(authority -> {
            AntPathRequestMatcher  matcher = new AntPathRequestMatcher(authority.getAuthorityUrl());
            return matcher.matches(request);
        }).map(Authority::getAuthorityId).collect(Collectors.toList());
        // 空判断
        if (authorities.size() == 0) {
            // 返回空代表任何人都能访问
            return null;
        }

        // 根据权限示例查询角色权限
        List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findByAuthorityIds(authorities);
        // 角色ID集合
        List<String> roleIds = roleAuthorityList.stream().map(roleAuthority -> roleAuthority.getRole().getRoleId()).collect(Collectors.toList());
        // 根据角色ID查询
        List<Role> roleList = roleRepository.findByRoleIds(roleIds);
        return roleList.stream().map(role -> new SecurityConfig(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
