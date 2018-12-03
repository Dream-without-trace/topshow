package com.luwei.services.specification.cms;

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
public class SpecificationAddDTO {

    @NotNull(message = "规格名称不能为空")
    @ApiModelProperty("规格名称")
    private String name;

    @NotNull(message = "商品id不能为空")
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty("价格")
    private Integer price;

    @NotNull(message = "规格图片不能为空")
    @ApiModelProperty("规格图片")
    private String picture;

    @NotNull(message = "库存不能为空")
    @ApiModelProperty("库存")
    private Integer inventory;

    @NotNull(message = "库存不能为空")
    @ApiModelProperty("备注")
    private String remark;
}
