package com.platform.admin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 管理员所拥有的角色关联表
 */
@Data
@Entity
@Table(name = "admin_role")
public class AdminRole implements Serializable {

    @Id
    @Column(name = "admin_role_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String adminRoleId;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
    private Administrator admin;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

}
