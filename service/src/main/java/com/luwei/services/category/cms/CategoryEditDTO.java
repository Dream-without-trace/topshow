package com.luwei.services.category.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Data
@ApiModel
public class CategoryEditDTO {

    @ApiModelProperty("商品分类id")
    private Integer categoryId;

    @ApiModelProperty("商品分类名称")
    private String name;

    @ApiModelProperty("分类图片")
    private String picture;

}
