package com.platform.common.utils.ajax;

import lombok.Data;

import java.util.List;

@Data
public class PageModule<T> {

    /** 当前页号 */
    private int currentPage;
    /** 每页展示条数 */
    private int size;
    /** 总条数 */
    private long totalCount;
    /** 总条数 */
    private int totalPage;
    /** 数据列表 */
    private List<T> list;

}
