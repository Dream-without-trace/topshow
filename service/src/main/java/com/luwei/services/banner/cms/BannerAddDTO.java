package com.luwei.services.banner.cms;

import com.luwei.common.enums.status.BannerStatus;
import com.luwei.common.enums.type.BannerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Data
@ApiModel
public class BannerAddDTO {

    @NotNull
    @ApiModelProperty("banner标题")
    private String title;

    @NotNull
    @ApiModelProperty("banner图")
    private String picture;

    @NotNull
    @ApiModelProperty("banner类型")
    private BannerType type;

    @NotNull
    @ApiModelProperty("跳转链接")
    private String url;

    @NotNull
    @ApiModelProperty("跳转的商品或活动的id")
    private Integer id;

    @ApiModelProperty("banner备注")
    private String remark;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("banner状态")
    private BannerStatus status;

}
