package com.luwei.services.order.detail.web;

import com.luwei.services.coupon.web.CouponOrderListVO;
import com.luwei.services.order.web.OrderGoodsSimpleVO;
import com.luwei.services.user.address.web.ReceivingAddressWebVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-07
 **/
public class OrderDetailWebVO {

    @ApiModelProperty("收货地址")
    private ReceivingAddressWebVO address;

    @ApiModelProperty("商家商品列表")
    private OrderGoodsSimpleVO orderGoods;

    @ApiModelProperty("优惠券")
    private CouponOrderListVO coupon;

    @ApiModelProperty("订单总额")
    private Integer orderTotal;

    @ApiModelProperty("运费总额")
    private Integer freightTotal;

}
