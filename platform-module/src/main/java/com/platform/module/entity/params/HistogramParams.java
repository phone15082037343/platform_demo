package com.platform.module.entity.params;

import com.platform.module.entity.DateRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "com.platform.module.entity.params.HistogramParams", description = "参数")
@Deprecated
public class HistogramParams {

    @ApiModelProperty(value = "时间间隔，取值为：【year、month、day、week】")
    private String interval = "month";

    @ApiModelProperty(value = "时间范围，开始时间 + 结束时间")
    private DateRange dateRange;

}
