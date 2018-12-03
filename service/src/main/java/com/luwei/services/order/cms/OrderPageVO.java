package com.luwei.services.order.cms;

import com.luwei.common.enums.status.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-10
 **/
@Data
@ApiModel
public class OrderPageVO {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("订单编号")
    private String orderOutNo;

    @ApiModelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("总价")
    private Integer total;

    @ApiModelProperty("运费")
    private Integer freight;

    @ApiModelProperty("收货人")
    private String username;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("收货地址")
    private String address;

    @ApiModelProperty("订单状态")
    private OrderStatus status;

    @ApiModelProperty("商品详情")
    private List<OrderGoodsDetailVO> orderDetail;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    @ApiModelProperty("快递单号")
    private String expressNumber;

    @ApiModelProperty("物流进度")
    private String logisticsSchedule;


}
