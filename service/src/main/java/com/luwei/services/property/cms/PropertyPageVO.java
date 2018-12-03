package com.luwei.services.property.cms;

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
public class PropertyPageVO {

    @ApiModelProperty("banner编号")
    private Integer bannerId;

    @ApiModelProperty("banner标题")
    private String title;

    @ApiModelProperty("banner图")
    private String picture;

    @ApiModelProperty("所属模块")
    private BannerType type;

    @ApiModelProperty("跳转链接")
    private String url;

}
