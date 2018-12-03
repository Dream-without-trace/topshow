package com.luwei.services.activity.web;

import com.luwei.common.enums.status.ActivityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class ActivityWebListVO implements Serializable {

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("活动状态")
    private ActivityStatus status;

    @ApiModelProperty("开始时间")
    private Date startTime;
}
