package com.platform.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Bucket implements Serializable {

    private String key;
    private long count;
    private List<Bucket> buckets;

}
