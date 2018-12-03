package com.luwei.services.banner.cms;

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
public class BannerQueryDTO {

    @ApiModelProperty("banner标题")
    private String title;

    @ApiModelProperty("所属模块")
    private BannerType type;

}
