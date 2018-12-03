package com.luwei.services.banner.cms;

import com.luwei.common.enums.type.CollectType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-01
 **/
@Data
@ApiModel
public class GoodsActivityVO {

    @ApiModelProperty("活动或商品id")
    private Integer id;

    @ApiModelProperty("活动或商品名称")
    private String name;

    @ApiModelProperty("活动或商品类型")
    private CollectType type;

    public GoodsActivityVO() {
    }

    public GoodsActivityVO(Integer id, String name, Integer type) {
        this.id = id;
        this.name = name;
        if (type == 1) {
            this.type = CollectType.GOODS;
        } else {
            this.type = CollectType.ACTIVITY;
        }
    }
}
