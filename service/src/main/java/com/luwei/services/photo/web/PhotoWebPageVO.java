package com.luwei.services.photo.web;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-27
 **/
@Data
@ApiModel
public class PhotoWebPageVO {

    private Integer userId;

    private List<String> photos;

    private Date createTime;



}
