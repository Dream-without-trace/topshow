package com.luwei.services.order.web;

import com.luwei.services.goods.web.GoodsSimpleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-29
 **/
@Data
@ApiModel
public class OrderGoodsSimpleVO {

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("商家名称")
    private String storeName;

    @ApiModelProperty("商家logo")
    private String logo;

    @ApiModelProperty("商品列表")
    private List<GoodsSimpleVO> goodsSimpleVOList;

}
