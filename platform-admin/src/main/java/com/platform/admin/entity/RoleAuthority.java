package com.platform.admin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色权限关联表
 */
@Data
@Entity
@Table(name = "role_authority")
public class RoleAuthority implements Serializable {

    @Id
    @Column(name = "role_authority_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String roleAuthorityId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    private Authority authority;

}
