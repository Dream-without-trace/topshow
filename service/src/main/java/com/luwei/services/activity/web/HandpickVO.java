package com.luwei.services.activity.web;

import com.luwei.services.shop.web.ShopListVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class HandpickVO {

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("活动介绍")
    private List<ActivityWebListVO> introductionList;

    @ApiModelProperty("亮点活动")
    private List<ActivityWebListVO> lightSpotList;

    @ApiModelProperty("本周推送")
    private List<ActivityWebListVO> pushList;

    @ApiModelProperty("门店")
    private List<ShopListVo> shopList;

}
