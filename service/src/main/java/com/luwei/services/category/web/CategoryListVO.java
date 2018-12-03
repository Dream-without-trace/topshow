package com.luwei.services.category.web;

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
public class CategoryListVO {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类图片")
    private String picture;

}
