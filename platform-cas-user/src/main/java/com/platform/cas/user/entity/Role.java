package com.platform.cas.user.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id", length = 32, nullable = false)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    private String roleId;

    /** 角色名称 */
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    /** 角色描述 */
    @Column(name = "description", length = 255)
    private String description;

}
