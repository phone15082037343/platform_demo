package com.platform.cas.conf;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Collections;

@Configuration
public class CasSecurityApplication {

    @Value("${cas.server.prefix}")
    private String casServerPrefix;

    @Value("${cas.server.login}")
    private String casServerLogin;

    @Value("${cas.server.logout}")
    private String casServerLogout;

    @Value("${cas.client.login}")
    private String casClientLogin;

    @Value("${cas.client.logout.url}")
    private String casClientLogout;

    @Value("${cas.client.logout.relative}")
    private String casClientLogoutRelative;

//    @Value("${cas.user.inmemory}")
//    private String casUserInMemory;

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casClientLogin);
        // 是否关闭单点登录，默认为false
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    /**
     * cas 验证入口
     */
    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties servicePropertie) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        // cas server认证地址
        entryPoint.setLoginUrl(casServerLogin);
        entryPoint.setServiceProperties(servicePropertie);
        return entryPoint;
    }

    /**
     * ticket 检验地址
     */
    @Bean
    public TicketValidator ticketValidator() {
        // 验证入口是${casServerPrefix}/proxyValidate
        return new Cas20ProxyTicketValidator(casServerPrefix);
    }

    /**
     * cas验证处理逻辑
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties, TicketValidator ticketValidator, UserDetailsService userDetailsService) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(ticketValidator);
        provider.setUserDetailsService(userDetailsService);
        provider.setKey("root");
        return provider;
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties serviceProperties, AuthenticationProvider provider) {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setServiceProperties(serviceProperties);
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        return filter;
    }

    /**
     * 接受cas服务器发送的注销请求
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casServerPrefix);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    /**
     * 将注销请求转发到cas server
     */
    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(casServerLogout, new SecurityContextLogoutHandler());
        // 设置客户端注销请求路径
        logoutFilter.setFilterProcessesUrl(casClientLogoutRelative);
        return logoutFilter;
    }

}
