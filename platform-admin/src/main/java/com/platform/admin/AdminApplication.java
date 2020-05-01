package com.platform.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableAdminServer
@EnableWebSecurity
@EnableDiscoveryClient
public class AdminApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
//        SpringApplication.run(AdminApplication.class, args);
        new SpringApplicationBuilder(AdminApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        super.configure(http);
    }

}
