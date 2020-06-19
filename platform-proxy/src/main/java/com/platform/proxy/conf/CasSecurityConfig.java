package com.platform.proxy.conf;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Collections;

@Configuration
public class CasSecurityConfig {

    @Value("${cas.server.prefix}")
    private String casServerPrefix;

    @Value("${cas.server.login}")
    private String casServerLogin;

    @Value("${cas.server.logout}")
    private String casServerLogout;

    @Value("${cas.client.prefix}")
    private String casClientPrefix;

    @Value("${cas.client.login}")
    private String casClientLogin;

    @Value("${cas.client.logout.relative}")
    private String casClientLogoutRelative;

    @Value("${cas.client.logout.absolute}")
    private String casClientLogoutAbsolute;

    /**
     * CAS入口点
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casServerLogin);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties);
        return casAuthenticationEntryPoint;
    }

    /**
     * 配置自身工程的根地址+/login/cas，表示是通过自身工程跳转到cas的登录页面
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casClientLogin);
        return serviceProperties;
    }

    /**
     * 认证过滤器
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties serviceProperties, CasAuthenticationProvider casAuthenticationProvider) {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setServiceProperties(serviceProperties);
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(casAuthenticationProvider)));
        return filter;
    }

    /**
     * 认证提供者
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(UserDetailsService userDetailsService, ServiceProperties serviceProperties, Cas20ServiceTicketValidator cas20ServiceTicketValidator) {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setUserDetailsService(userDetailsService);
        casAuthenticationProvider.setServiceProperties(serviceProperties);
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator);
        casAuthenticationProvider.setKey("demo");
        return casAuthenticationProvider;
    }

    /**
     * 单点登录服务器cas的访问路径，用来校验票据
     */
    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casServerPrefix);
    }

    /**
     * 单点登出
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casServerPrefix);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    /**
     * 客户端进行单点登出
     */
    @Bean
    public LogoutFilter logoutFilter() {
        // cas登出的路径
        LogoutFilter logoutFilter = new LogoutFilter(casServerLogout, new SecurityContextLogoutHandler());
        // 在我们的系统中，只要请求/logout/cas，就相当于访问了上面配置的cas的登出路径
        logoutFilter.setFilterProcessesUrl(casClientLogoutRelative);
        return logoutFilter;
    }

}
