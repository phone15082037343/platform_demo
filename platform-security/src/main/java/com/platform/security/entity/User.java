package com.platform.security.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_info")
public class User implements Serializable {

    @Id
    @Column(name = "user_id", length = 32)
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String userId;

    @Column(name = "username", unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

}
