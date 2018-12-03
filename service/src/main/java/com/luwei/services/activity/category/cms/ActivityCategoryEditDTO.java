package com.luwei.services.activity.category.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Data
@ApiModel
public class ActivityCategoryEditDTO {

    @NotNull(message = "活动id不能为空")
    @ApiModelProperty("活动类别id")
    private Integer activityCategoryId;

    @ApiModelProperty("活动标题标题")
    private String title;

    @ApiModelProperty("备注")
    private String remark;

}
