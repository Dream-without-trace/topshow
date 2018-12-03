package com.luwei.services.activity.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-23
 **/
@Data
@ApiModel
public class ActivityApplyDTO {

    @NotNull
    @ApiModelProperty("活动id")
    private Integer activityId;

    @NotNull
    @ApiModelProperty("数量")
    private Integer count;

    @NotNull
    @ApiModelProperty("用户ID")
    private Integer userId;

}
