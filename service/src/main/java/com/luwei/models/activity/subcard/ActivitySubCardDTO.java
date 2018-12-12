package com.luwei.models.activity.subcard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: topshow
 * @description: 活动次卡
 * @author: ZhangHongJie
 * @create: 2018-12-12 15:07
 **/
@Data
@ApiModel
public class ActivitySubCardDTO {

    @ApiModelProperty("活动次卡Id")
    private Integer activitySubCardId;

    @ApiModelProperty("活动Id")
    private Integer activityId;

    @ApiModelProperty("活动次卡标题")
    private String title;

    @ApiModelProperty("活动次卡封面")
    private String picture;

    @ApiModelProperty("活动次卡描述")
    private String detail;

    @ApiModelProperty("活动次卡价格（单位是分）")
    private Integer price;

    @ApiModelProperty("活动次卡次数")
    private Integer frequency;

}
