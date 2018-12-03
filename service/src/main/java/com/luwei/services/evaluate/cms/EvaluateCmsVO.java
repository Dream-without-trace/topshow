package com.luwei.services.evaluate.cms;

import com.luwei.common.enums.type.FlagType;
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
public class EvaluateCmsVO {

    @ApiModelProperty("评论id")
    private Integer evaluateId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("评价内容")
    private String content;

    @ApiModelProperty("图片")
    private List<String> picture;

    @ApiModelProperty("显示隐藏")
    private FlagType flagType;

}
