package com.platform.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Deprecated
public class Buckets implements Serializable {

    private List<Bucket> buckets;

}
