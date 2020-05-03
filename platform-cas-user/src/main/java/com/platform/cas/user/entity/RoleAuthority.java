package com.platform.cas.user.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role_authority")
public class RoleAuthority {

    @Id
    @Column(name = "role_authority_id", length = 32, nullable = false)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    private String roleAuthorityId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

}
