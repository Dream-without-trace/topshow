package com.luwei.services.evaluate.web;

import com.luwei.common.enums.type.EvaluateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-10
 **/
@Data
@ApiModel
public class EvaluateDTO {

    @ApiModelProperty("活动或商品的订单id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("评价内容")
    private String content;

    @ApiModelProperty("图片数组")
    private List<String> picture;

    @ApiModelProperty("活动或商品id")
    private Integer tripartiteId;

    @ApiModelProperty("评价类型")
    private EvaluateType evaluateType;

}
