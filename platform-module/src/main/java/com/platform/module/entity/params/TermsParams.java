package com.platform.module.entity.params;

import com.platform.module.entity.DateRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.platform.module.entity.params.TermsParams", description = "参数")
@Deprecated
public class TermsParams implements Serializable {

    /** 条数 */
    @ApiModelProperty(value = "返回条数，默认10条")
    private int size = 10;
    /** 时间范围 */
    @ApiModelProperty(value = "时间范围，开始时间 + 结束时间")
    private DateRange dateRange;

}
