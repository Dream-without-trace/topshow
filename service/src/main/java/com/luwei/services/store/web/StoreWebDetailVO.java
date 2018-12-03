package com.luwei.services.store.web;

import com.luwei.common.enums.status.StoreStatus;
import com.luwei.services.goods.web.StoreGoodsListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p> 商家详情
 *
 * @author Leone
 * @since 2018-08-28
 **/
@Data
@ApiModel
public class StoreWebDetailVO {

    @ApiModelProperty("商家id")
    private Integer storeId;

    @ApiModelProperty("商家图片")
    private String picture;

    @ApiModelProperty("商家名称")
    private String name;

    @ApiModelProperty("商家详情")
    private String description;

    @ApiModelProperty("商家地址")
    private String address;

    @ApiModelProperty("商家邮箱")
    private String email;

    @ApiModelProperty("商家logo")
    private String logo;

    @ApiModelProperty("商家手机号")
    private String phone;

    @ApiModelProperty("商品列表")
    private List<StoreGoodsListVO> storeGoodsList;


}
