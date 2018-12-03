package com.luwei.services.specification.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class SpecificationWebVO {

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("规格价格")
    private Integer price;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("库存")
    private Integer inventory;

}
