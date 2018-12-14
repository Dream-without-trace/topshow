package com.luwei.models.activity.subcard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "活动Id不能为空")
    private Integer activityId;
    @ApiModelProperty("活动次卡标题")
    @NotNull(message = "活动次卡标题不能为空")
    private String title;
    @ApiModelProperty("活动次卡封面")
    private String picture;
    @ApiModelProperty("活动次卡描述")
    private String detail;
    @ApiModelProperty("活动次卡价格（单位是分）")
    @NotNull(message = "活动次卡价格不能为空")
    private Integer price;
    @ApiModelProperty("活动次卡次数")
    @NotNull(message = "活动次卡次数不能为空")
    private Integer frequency;

}
