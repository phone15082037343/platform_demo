package com.platform.security.conf;


import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
//
//    @Autowired
//    private MyUserDetailService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CasAuthenticationFilter casAuthenticationFilter;

    @Autowired
    private SingleSignOutFilter singleSignOutFilter;

    @Autowired
    private LogoutFilter logoutFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/fonts/**").permitAll()
//                .antMatchers("/favicon.ico").permitAll()
//                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .httpBasic()
                .and()
                .addFilter(casAuthenticationFilter)
                .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
                .addFilterBefore(logoutFilter, LogoutFilter.class);

    }

//    @Override
//    public void configure(WebSecurity web) {
//        // 放行静态资源
//        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/login/**", "/resources/**", "/favicon.ico");
//    }

}
