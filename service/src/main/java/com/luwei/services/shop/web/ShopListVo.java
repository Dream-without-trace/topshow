package com.luwei.services.shop.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: topshow
 * @description: 门店列表
 * @author: ZhangHongJie
 * @create: 2018-12-06 22:07
 **/
@Data
public class ShopListVo {

    @ApiModelProperty("门店Id")
    private Integer shopId;

    @ApiModelProperty("门店名称")
    private String title;

    @ApiModelProperty("封面")
    private String picture;

    public ShopListVo() {
    }

    public ShopListVo(Integer shopId, String title, String picture) {
        this.shopId = shopId;
        this.title = title;
        this.picture = picture;
    }
}
