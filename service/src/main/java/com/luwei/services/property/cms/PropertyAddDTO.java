package com.luwei.services.property.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class PropertyAddDTO {

    @NotNull(message = "属性名称不能为空")
    @ApiModelProperty("属性名称")
    private String name;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty("价格")
    private Integer price;

    @NotNull(message = "属性图片不能为空")
    @ApiModelProperty("属性图片")
    private String picture;

    @NotNull(message = "库存不能为空")
    @ApiModelProperty("库存")
    private Integer inventory;

    @NotNull(message = "库存不能为空")
    @ApiModelProperty("备注")
    private Integer remark;

}
