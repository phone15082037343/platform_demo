package com.platform.provider.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {

    @Id
    @Column(name = "admin_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String adminId;

    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "disable")
    private boolean disable;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update")
    private Date lastUpdate;

}
