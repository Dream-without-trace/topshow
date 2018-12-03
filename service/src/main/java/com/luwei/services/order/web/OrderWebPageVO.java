package com.luwei.services.order.web;

import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.services.order.detail.web.OrderGoodsWebVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-27
 **/
@Data
@ApiModel
public class OrderWebPageVO {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("外部订单号")
    private String orderOutNo;

    @ApiModelProperty("商品列表")
    private List<OrderGoodsWebVO> orderGoods;

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("订单状态")
    private OrderStatus status;

    public OrderWebPageVO() {
    }

    public OrderWebPageVO(Integer orderId, Integer userId, String orderOutNo, List<OrderGoodsWebVO> orderGoods, Integer storeId, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderOutNo = orderOutNo;
        this.orderGoods = orderGoods;
        this.storeId = storeId;
        this.status = status;
    }
}
