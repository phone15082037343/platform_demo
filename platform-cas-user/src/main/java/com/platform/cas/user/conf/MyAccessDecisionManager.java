package com.platform.cas.user.conf;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判定 是否含有权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes != null && configAttributes.size() > 0) {
            for (ConfigAttribute configAttribute : configAttributes) {
                Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
                for (GrantedAuthority grantedAuthority : grantedAuthorities) {
                    if (configAttribute.getAttribute().equals(grantedAuthority.getAuthority())) {
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("no privilege");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
