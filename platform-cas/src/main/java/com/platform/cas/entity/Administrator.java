package com.platform.cas.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable, UserDetails {

    @Id
    @Column(name = "admin_id", length = 32)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    private String adminId;

    /** 登录名 */
    @Column(name = "username", length = 50)
    private String username;

    /** 密码 */
    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "roles")
    private String roles;

    @Transient
    private List<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
