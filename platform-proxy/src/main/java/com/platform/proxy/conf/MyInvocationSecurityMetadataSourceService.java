package com.platform.proxy.conf;

import com.platform.admin.entity.Authority;
import com.platform.admin.entity.Role;
import com.platform.admin.entity.RoleAuthority;
import com.platform.admin.repository.AuthorityRepository;
import com.platform.admin.repository.RoleAuthorityRepository;
import com.platform.admin.repository.RoleRepository;
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
     *
     * 查询访问该URL所需的角色，为空代表不进行权限控制
     * TODO 这里直接从数据库查询，可以考虑放在缓存里面
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getHttpRequest();
        System.out.println("uri:" + request.getRequestURI());

//        // TODO 在这里设置白名单
//        AntPathRequestMatcher  antPathRequestMatcher = new AntPathRequestMatcher("/**/*.html");
//        if (antPathRequestMatcher.matches(request)) {
//            return null;
//        }

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
