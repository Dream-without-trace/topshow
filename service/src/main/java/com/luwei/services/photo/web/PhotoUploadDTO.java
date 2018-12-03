package com.luwei.services.photo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-01
 **/
@Data
@ApiModel
public class PhotoUploadDTO {

    @NotNull(message = "用户的id不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotEmpty(message = "图片数组不能为空")
    @ApiModelProperty("图片数组")
    private List<String> photos;

}
