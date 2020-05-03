package com.platform.cas.user.conf;

import com.platform.cas.user.entity.RoleAuthority;
import com.platform.cas.user.repository.RoleAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    /**
     * 返回访问该资源需要的角色，返回空代表都能访问
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        // TODO 注意：这里每次访问资源都会查询数据库
        // 数据库查询所有的角色权限
        List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findAll();
        Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
        roleAuthorityList.forEach(roleAuthority -> {
            String url = roleAuthority.getAuthority().getUrl();
            if (map.containsKey(url)) {
                Collection<ConfigAttribute> configAttributes = map.get(url);
                configAttributes.add(new SecurityConfig(roleAuthority.getRole().getName()));
            } else {
                Collection<ConfigAttribute> configAttributes = new LinkedList<>();
                configAttributes.add(new SecurityConfig(roleAuthority.getRole().getName()));
                map.put(url, configAttributes);
            }
        });

        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            if (new AntPathRequestMatcher(entry.getKey()).matches(request)) {
                return entry.getValue();
            }
        }

        return null;
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
