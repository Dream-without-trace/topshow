package com.luwei.services.goods.cms;

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
public class GoodsQueryDTO {

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品分类id")
    private Integer categoryId;

}
