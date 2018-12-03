package com.luwei.services.banner.cms;

import com.luwei.common.enums.status.BannerStatus;
import com.luwei.common.enums.type.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class BannerPageVO {

    @ApiModelProperty("banner编号")
    private Integer bannerId;

    @ApiModelProperty("banner标题")
    private String title;

    @ApiModelProperty("banner图")
    private String picture;

    @ApiModelProperty("banner类型")
    private BannerType type;

    @ApiModelProperty("跳转链接")
    private String url;

    @ApiModelProperty("banner跳转商品或活动的id")
    private Integer id;

    @ApiModelProperty("绑定商品或活动名称")
    private String name;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("banner状态")
    private BannerStatus status;

}
