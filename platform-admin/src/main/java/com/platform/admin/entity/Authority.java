package com.platform.admin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限实体，针对URL
 */
@Data
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @Column(name = "authority_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String authorityId;

    @Column(name = "authority_name")
    private String authorityName;

    @Column(name = "authority_url", nullable = false)
    private String authorityUrl;

}
