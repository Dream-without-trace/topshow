package com.luwei.services.goods.web;

import com.luwei.common.enums.type.FlagType;
import com.luwei.services.evaluate.web.EvaluateWebVO;
import com.luwei.services.specification.web.SpecificationWebVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-25
 **/
@Data
@ApiModel
public class GoodsDetailVO {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("商家头像")
    private String logo;

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("销量")
    private Integer sales;

    @ApiModelProperty("商品价格")
    private Integer price;

    @ApiModelProperty("商品参数")
    private String parameters;

    @ApiModelProperty("商品规格")
    private List<SpecificationWebVO> specificationList;

    @ApiModelProperty("商家名称")
    private String storeName;

    @ApiModelProperty("商家电话")
    private String phone;

    @ApiModelProperty("商品详情")
    private String detail;

    @ApiModelProperty("是否收藏")
    private FlagType collect;

    @ApiModelProperty("客服id")
    private String customerId;


}
