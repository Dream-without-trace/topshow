package com.luwei.services.category.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Data
@ApiModel
public class CategoryPageVO {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类图片")
    private String picture;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
