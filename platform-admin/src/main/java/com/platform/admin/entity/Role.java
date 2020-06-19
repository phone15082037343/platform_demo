package com.platform.admin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色实体
 */
@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

}
