package com.platform.cas.user.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @Column(name = "authority_id", length = 32, nullable = false)
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    private String authorityId;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

}
