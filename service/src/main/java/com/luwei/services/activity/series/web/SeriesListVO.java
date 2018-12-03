package com.luwei.services.activity.series.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class SeriesListVO {

    @ApiModelProperty("活动系列id")
    private Integer activitySeriesId;

    @ApiModelProperty("系列活动标题")
    private String title;

}
