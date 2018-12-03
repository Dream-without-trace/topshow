package com.luwei.services.activity.category.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-04
 **/
@Data
@ApiModel
public class ActivityCategoryListVO {

    @ApiModelProperty("活动分类id")
    private Integer activityCategoryId;

    @ApiModelProperty("活动分类标题")
    private String title;

}
