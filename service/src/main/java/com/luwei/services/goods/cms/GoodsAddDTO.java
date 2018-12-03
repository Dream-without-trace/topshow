package com.luwei.services.goods.cms;

import com.luwei.common.enums.type.GoodsType;
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
public class GoodsAddDTO {

    @ApiModelProperty("商家信息")
    private Integer storeId;

    @NotNull
    @ApiModelProperty("商品名称")
    private String name;

    @NotNull
    @ApiModelProperty("商品类别")
    private Integer categoryId;

    @NotNull
    @ApiModelProperty("商品详情")
    private String detail;

    @NotNull
    @ApiModelProperty("商品参数")
    private String parameters;

    @NotNull
    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("运费")
    private Integer freight;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("商品类型")
    private GoodsType goodsType;

}
