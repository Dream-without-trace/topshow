package com.luwei.services.evaluate.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
@ApiModel
public class EvaluateWebVO {

    @ApiModelProperty("评价id")
    private Integer evaluateId;

    @ApiModelProperty("评论时间")
    private Date createTime;

    @ApiModelProperty("图片列表")
    private List<String> pictureList;

    @JsonIgnore
    private String picture;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("评论内容")
    private String content;

    public EvaluateWebVO() {
    }

    public EvaluateWebVO(Integer evaluateId, Date createTime, String picture, String avatarUrl, String nickname, String content) {
        this.evaluateId = evaluateId;
        this.createTime = createTime;
        this.picture = picture;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.content = content;
    }

}

