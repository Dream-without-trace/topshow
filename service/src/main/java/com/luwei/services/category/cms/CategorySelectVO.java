package com.luwei.services.category.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Data
@ApiModel
public class CategorySelectVO {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("类别名称")
    private String name;

}
