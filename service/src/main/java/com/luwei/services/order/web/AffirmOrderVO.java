package com.luwei.services.order.web;

import com.luwei.services.coupon.web.CouponOrderListVO;
import com.luwei.services.user.address.web.ReceivingAddressWebVO;
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
public class AffirmOrderVO {

    @ApiModelProperty("收货地址")
    private ReceivingAddressWebVO address;

    @ApiModelProperty("商家商品列表")
    private List<OrderGoodsSimpleVO> orderGoodsList;

    @ApiModelProperty("优惠券列表")
    private List<CouponOrderListVO> couponList;

    @ApiModelProperty("订单总额")
    private Integer orderTotal;

    @ApiModelProperty("运费总额")
    private Integer freightTotal;

}
