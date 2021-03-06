package com.luwei.services.activity.series.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Data
@ApiModel
public class ActivitySeriesPageVO {

    @ApiModelProperty("活动类别id")
    private Integer activitySeriesId;

    @ApiModelProperty("活动标题标题")
    private String title;

    @ApiModelProperty("活动系列数")
    private Integer count;

    @ApiModelProperty("备注")
    private String remark;

}
