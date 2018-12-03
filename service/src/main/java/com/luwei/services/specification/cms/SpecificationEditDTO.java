package com.luwei.services.specification.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-22
 **/
@Data
@ApiModel
public class SpecificationEditDTO {

    @NotNull
    @ApiModelProperty("商品规格id不能为空")
    private Integer specificationId;

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("规格图片")
    private String picture;

    @ApiModelProperty("库存")
    private Integer inventory;

    @ApiModelProperty("备注")
    private String remark;
}
